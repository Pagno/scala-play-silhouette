package models.daos.impl


import java.util.UUID
import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import models.User
import models.daos.{DBUser, UserDAO}
import models.daos.impl.UserDAOImpl._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}


class UserDAOImpl @Inject()(
                             protected val dbConfigProvider: DatabaseConfigProvider
                           )(
                             implicit ex: ExecutionContext
                           ) extends UserDAO {
  /**
    * Finds a user by its login info.
    *
    * @param loginInfo The login info of the user to find.
    * @return The found user or None if no user for the given login info could be found.
    */
  def find(loginInfo: LoginInfo): Future[Option[User]] = {
    System.out.println("A: "+loginInfo);
    implicit val getUserResult = GetResult(r => DBUser(r.<<, r.<<,r.<<, r.<<,r.<<, r.<<))

    db.run(
      sql"""select u.* from LOGIN_INFO li
        join USER_LOGIN_INFO uli on li.id = uli.LOGIN_INFO_ID
        join user u on u.id = uli.USER_ID where li.PROVIDER_ID = ${loginInfo.providerID} and li.PROVIDER_KEY = ${loginInfo.providerKey};""".as[DBUser].headOption.map { dbUserOption =>
        dbUserOption.map { user =>
          User(UUID.fromString(user.id),
            user.firstName,
            user.email,
            user.firstName,
            user.lastName,
            user.avatarURL,
            user.activated,
            loginInfo)
        }
      }
    )

  }

  /**
    * Finds a user by its user ID.
    *
    * @param userID The ID of the user to find.
    * @return The found user or None if no user for the given ID could be found.
    */
  def find(userID: UUID) = {
    Future.successful(users.get(userID));
  }

  /**
    * Saves a user.
    *
    * @param user The user to save.
    * @return The saved user.
    */
  def save(user: User) = {
    val userUuid = UUID.randomUUID()
    val ret=db.run(
      sqlu"""
            INSERT INTO scalaplaytest.`USER` (ID, FIRST_NAME, LAST_NAME, EMAIL, AVATAR_URL, ACTIVATED)
            VALUES(${user.userID.toString}, ${user.firstName}, ${user.lastName}, ${user.email},${user.avatarURL}, ${user.activated})
            """
    ).map(_ => user)

    db.run(
      sqlu"""
            INSERT INTO scalaplaytest.LOGIN_INFO (ID, PROVIDER_ID, PROVIDER_KEY)
            VALUES(${userUuid.toString}, ${user.loginInfo.providerID }, ${user.loginInfo.providerKey})
            """
    )

    db.run(
      sqlu"""
            INSERT INTO scalaplaytest.USER_LOGIN_INFO (USER_ID, LOGIN_INFO_ID)
            VALUES(${user.userID.toString}, ${userUuid.toString});
            """
    )

    ret
  }

}

/**
  * The companion object.
  */
object UserDAOImpl {

  /**
    * The list of users.
    */
  val users: mutable.HashMap[UUID, User] = mutable.HashMap()


}