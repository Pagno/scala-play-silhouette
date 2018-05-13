package controllers


import com.google.inject.Inject
import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.util.Credentials
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import com.mohiva.play.silhouette.impl.providers._
import io.swagger.annotations.{ApiImplicitParam, ApiImplicitParams, ApiOperation}
import models.Token
import models.daos.UserDAO
import models.json.CredentialFormat
import org.webjars.play.WebJarAssets
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._
import service.UserService
import utils.auth.DefaultEnv
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CredentialsAuthController @Inject()(
          silhouette: Silhouette[DefaultEnv],
          socialProviderRegistry: SocialProviderRegistry,
          implicit val webJarAssets: WebJarAssets,
          components: ControllerComponents,
          userDAO: UserDAO,
          userService: UserService,
          credentialsProvider: CredentialsProvider,
          configuration: Configuration)
                          extends AbstractController(components) with play.api.i18n.I18nSupport {

  implicit val credentialFormat = CredentialFormat.restFormat

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
  def submit = silhouette.UnsecuredAction.async(parse.json[Credentials]) { implicit request =>
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

}
