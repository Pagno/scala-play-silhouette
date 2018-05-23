package controllers


import java.util.UUID

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.services.AvatarService
import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.util.{Credentials, PasswordHasherRegistry}
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import com.mohiva.play.silhouette.impl.providers._
import io.swagger.annotations.{ApiImplicitParam, ApiImplicitParams, ApiOperation}
import models.{SignUp, Token, User}
import models.daos.UserDAO
import models.json.CredentialFormat
import org.webjars.play.WebJarAssets
import play.api.Configuration
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import service.UserService
import utils.auth.DefaultEnv

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.i18n.I18nSupport
import utils.responses.rest.Bad

class CredentialsAuthController @Inject()(
          silhouette: Silhouette[DefaultEnv],
          implicit val webJarAssets: WebJarAssets,
          components: ControllerComponents,
          userDAO: UserDAO,
          userService: UserService,
          credentialsProvider: CredentialsProvider,
          authInfoRepository: AuthInfoRepository,
          passwordHasherRegistry: PasswordHasherRegistry,
          avatarService: AvatarService,
          configuration: Configuration)
                          extends AbstractController(components) with I18nSupport {

  implicit val credentialFormat = CredentialFormat.restFormat
  implicit val signUpFormat = Json.format[SignUp]

  def view = silhouette.UnsecuredAction.async(parse.json) { implicit request =>
    Future.successful(Ok("A"))
  }


  /**
    * Handles the submitted form.
    *
    * @return The result to display.
    */
  @ApiOperation(value = "Get authentication token", response = classOf[Token])
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "Credentials",
        required = true,
        dataType = "com.mohiva.play.silhouette.api.util.Credentials",
        paramType = "body"
      )
    )
  )
  def authenticate = silhouette.UnsecuredAction.async(parse.json[Credentials]) { implicit request =>
    val credentials =  Credentials(request.body.identifier, request.body.password)
    credentialsProvider
      .authenticate(credentials)
      .flatMap { loginInfo =>
        userService.retrieve(loginInfo).flatMap {
          case Some(user) if !user.activated =>
            Future.failed(new IdentityNotFoundException("Couldn't find user"))
          case Some(user) =>
            val config = configuration.underlying
            silhouette.env.authenticatorService
              .create(loginInfo)
              .map {
                case authenticator => authenticator
              }
              .flatMap { authenticator =>
                silhouette.env.eventBus.publish(LoginEvent(user, request))
                silhouette.env.authenticatorService
                  .init(authenticator)
                  .flatMap { token =>
                    silhouette.env.authenticatorService
                      .embed(
                        token,
                        Ok(
                          Json.toJson(
                            Token(
                              token,
                              expiresOn = authenticator.expirationDateTime
                            )
                          )
                        )
                      )
                  }
              }
          case None =>
            Future.failed(new IdentityNotFoundException("Couldn't find user"))
        }
      }
      .recover {
        case _: ProviderException =>
          Forbidden
      }
  }

  def logged =  silhouette.SecuredAction.async { implicit request =>

    Future.successful(Ok(Json.obj("result" -> "qwerty1234")))
  }


  @ApiOperation(value = "Register and get authentication token", response = classOf[Token])
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "SignUp",
        required = true,
        dataType = "models.security.SignUp",
        paramType = "body"
      )
    )
  )
  def signUp = Action.async(parse.json) { implicit request =>
    request.body.validate[SignUp].map { signUp =>
      val loginInfo = LoginInfo(CredentialsProvider.ID, signUp.identifier)
      userService.retrieve(loginInfo).flatMap {
        case None => /* user not already exists */
          val user = User(UUID.randomUUID(),signUp.firstName,signUp.lastName, signUp.firstName+" "+ signUp.lastName,signUp.email,None,false,  loginInfo)
          // val plainPassword = UUID.randomUUID().toString.replaceAll("-", "")
          val authInfo = passwordHasherRegistry.current.hash(signUp.password)
          for {
            avatar <- avatarService.retrieveURL(signUp.email)
            userToSave <- userService.save(user.copy(avatarURL = avatar))
            authInfo <- authInfoRepository.add(loginInfo, authInfo)
            authenticator <- silhouette.env.authenticatorService.create(loginInfo)
            token <- silhouette.env.authenticatorService.init(authenticator)
            result <- silhouette.env.authenticatorService.embed(token,
              Ok(Json.toJson(Token(token = token, expiresOn = authenticator.expirationDateTime)))
            )
          } yield {
            val url = routes.ApplicationController.index().absoluteURL()
            /*mailerClient.send(Email(
              subject = Messages("email.sign.up.subject"),
              from = Messages("email.from"),
              to = Seq(user.email),
              bodyText = Some(views.txt.emails.signUp(user, url).body),
              bodyHtml = Some(views.html.emails.signUp(user, url).body)
            ))*/
            silhouette.env.eventBus.publish(SignUpEvent(user, request))
            silhouette.env.eventBus.publish(LoginEvent(user, request))
            result
          }
        case Some(_) => /* user already exists! */
          Future(Conflict(Json.toJson(Bad(message = "user already exists"))))
      }
    }.recoverTotal {
      case error =>
        Future.successful(BadRequest(Json.toJson(Bad(message = JsError.toJson(error)))))
    }
  }

}
