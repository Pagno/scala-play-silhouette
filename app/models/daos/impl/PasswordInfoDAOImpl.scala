package models.daos.impl

import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

/**
  * An implementation of the auth info DAO which stores the data in database.
  */
class PasswordInfoDAOImpl @Inject()(
                protected val dbConfigProvider: DatabaseConfigProvider
               )(implicit ex: ExecutionContext) extends DelegableAuthInfoDAO[PasswordInfo] {

  lazy val db = Database.forConfig("testdb")

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {

    implicit val getUserResult = GetResult(r => PasswordInfo(r.<<, r.<<, r.<<))

    db.run(
      sql"""select pi.HASHER,pi.PASSWORD,pi.SALT from PASSWORD_INFO pi
          join LOGIN_INFO li on li.ID = pi.LOGIN_INFO_ID
          where li.PROVIDER_ID = ${loginInfo.providerID}
            and li.PROVIDER_KEY =${loginInfo.providerKey}""".as[PasswordInfo].headOption.map { dbPasswordInfoOption =>
        dbPasswordInfoOption.map { dbPasswordInfo =>
          PasswordInfo(dbPasswordInfo.hasher, dbPasswordInfo.password, dbPasswordInfo.salt)
        }
      }
    )
  }

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    db.run(
      sqlu"""insert into PASSWORD_INFO
            VALUES (${authInfo.hasher},${authInfo.password},${authInfo.salt},(select li.id from LOGIN_INFO li where li.PROVIDER_ID = ${loginInfo.providerID} and li.PROVIDER_KEY =${loginInfo.providerKey}));"""
    ).map(_ => authInfo)
  }

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    db.run(
      sqlu"""update PASSWORD_INFO set HASHER = ${authInfo.hasher}, PASSWORD = ${authInfo.password}, SALT=${authInfo.salt}
        where LOGIN_INFO_ID in (
            select li.id from LOGIN_INFO li li where li.PROVIDER_ID = ${loginInfo.providerID}
            and li.PROVIDER_KEY =${loginInfo.providerKey}
        );
      """
      ).map(_ => authInfo)
  }

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] ={
    db.run(
      sqlu"""insert into PASSWORD_INFO
            VALUES (${authInfo.hasher},${authInfo.password},${authInfo.salt},(select li.id from LOGIN_INFO li where li.PROVIDER_ID = ${loginInfo.providerID} and li.PROVIDER_KEY =${loginInfo.providerKey}));"""
    ).map(_ => authInfo)
  }

  override def remove(loginInfo: LoginInfo)  = {
    implicit val getUserResult = GetResult(r => ())

    //db.run(passwordInfos.filter(_.loginInfoId in loginInfoQuery(loginInfo).map(_.id)).delete).map(_ => ())
    db.run(
      sqlu"""delete from PASSWORD_INFO pi
        where pi.LOGIN_INFO_ID in (
        select li.id from LOGIN_INFO li where li.PROVIDER_ID = ${loginInfo.providerID}
          and li.PROVIDER_KEY =${loginInfo.providerKey}
        );"""
    ).map(_ => ())
  }

}