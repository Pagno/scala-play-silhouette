package services.impl

import java.util.UUID
import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.CommonSocialProfile
import models.User
import models.daos.UserDAO
import services.UserService
import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl @Inject()(
            implicit ec: ExecutionContext,
            userDAO: UserDAO
          )  extends UserService{
  /**
    * Retrieves a user that matches the specified ID.
    *
    * @param id The ID to retrieve a user.
    * @return The retrieved user or None if no user could be retrieved for the given ID.
    */
  def retrieve(id: UUID) = userDAO.find(id)

  /**
    * Saves a user.
    *
    * @param loginInfo The user to save.
    * @return The saved user.
    */

  def retrieve(loginInfo: LoginInfo): Future[Option[User]] = userDAO.find(loginInfo)
  override def save(user: User) = userDAO.save(user)

  /**
    * Saves the social profile for a user.
    *
    * If a user exists for this profile then update the user, otherwise create a new user with the given profile.
    *
    * @param profile The social profile to save.
    * @return The user for whom the profile was saved.
    */
  def save(profile: CommonSocialProfile) = {
    userDAO.find(profile.loginInfo).flatMap {
      case Some(user) => // Update user with profile
        userDAO.save(user.copy(
          firstName = profile.firstName.get,
          lastName = profile.lastName.get,
          fullName = profile.fullName.get,
          email = profile.email.get,
          avatarURL = profile.avatarURL
        ))
      case None => // Insert a new user
        userDAO.save(User(
          userID = UUID.randomUUID(),
          loginInfo = profile.loginInfo,
          firstName = profile.firstName.get,
          lastName = profile.lastName.get,
          fullName = profile.fullName.get,
          email = profile.email.get,
          avatarURL = profile.avatarURL,
          activated = true
        ))
    }
  }

}
