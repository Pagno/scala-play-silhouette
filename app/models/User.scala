package models

import java.util.UUID
import play.api.libs.json._
import com.mohiva.play.silhouette.api.{Identity, LoginInfo}

case class User (
                  userID: UUID,
                  firstName: String,
                  lastName: String,
                  fullName: String,
                  email:String,
                  avatarURL: Option[String],
                  activated: Boolean,
                  loginInfo: LoginInfo
                        ) extends Identity {

  /**
    * Tries to construct a name.
    *
    * @return Maybe a name.

  def name = fullName.orElse {
    firstName -> lastName match {
      case (Some(f), Some(l)) => Some(f + " " + l)
      case (Some(f), None) => Some(f)
      case (None, Some(l)) => Some(l)
      case _ => None
    }
  }
*/

}


object User {
  implicit val userFormats = Json.format[User]

  def userWrites(user: User) = {
    Json.toJson(user)
  }

  def userReads(jsonUser: JsValue) = {
    jsonUser.as[User]
  }
}