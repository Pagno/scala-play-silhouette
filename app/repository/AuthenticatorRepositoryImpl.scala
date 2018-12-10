package repository

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{LoginInfo, StorableAuthenticator}
import com.mohiva.play.silhouette.api.repositories.AuthenticatorRepository
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import models.daos.{DAO, DBJWTAuthenticator}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.language.postfixOps
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import com.github.tototoshi.slick.MySQLJodaSupport._

/**
  * Implementation of the authenticator repository which uses the database layer to persist the authenticator.
  */
class AuthenticatorRepositoryImpl  @Inject()(implicit ec: ExecutionContext)
  extends AuthenticatorRepository[JWTAuthenticator]  {

  lazy val db = Database.forConfig("testdb")

  final val maxDuration = 12 hours

  override def find(id: String): Future[Option[JWTAuthenticator]] = {

    implicit val getUserResult = GetResult(r => DBJWTAuthenticator(r.<<, r.<<, r.<<, r.<<, r.<<))

    val asd = db.run(
      sql"""select ja.ID,li.PROVIDER_ID,li.PROVIDER_KEY,ja.LAST_USED_DATE_TIME,ja.EXPIRATION_DATE_TIME
            from JWT_AUTHENTICATOR ja
            join LOGIN_INFO li on li.ID=ja.LOGIN_INFO_ID
            where ja.ID = ${id}
         """.as[DBJWTAuthenticator].headOption
    )

    val asdb = asd . map{dbJWTAuthenticator => dbJWTAuthenticator
      .map {dbJWTAuthenticator =>
        new JWTAuthenticator(dbJWTAuthenticator.id,new LoginInfo(dbJWTAuthenticator.providerId,dbJWTAuthenticator.providerKey),dbJWTAuthenticator.lastUsedDateTime,dbJWTAuthenticator.expirationDateTime,None,None) }
    }

    asdb

  }

  override def add(authenticator: JWTAuthenticator): Future[JWTAuthenticator] = {
    db.run(
      sqlu"""insert into JWT_AUTHENTICATOR
            VALUES (${authenticator.id},(SELECT id FROM LOGIN_INFO WHERE PROVIDER_ID=${authenticator.loginInfo.providerID} and PROVIDER_KEY = ${authenticator.loginInfo.providerKey}),${authenticator.lastUsedDateTime},${authenticator.expirationDateTime})"""
    ).map(_ => authenticator)
  }

  override def update(authenticator: JWTAuthenticator): Future[JWTAuthenticator] = {
    db.run(
      sqlu"""update JWT_AUTHENTICATOR
            set LOGIN_INFO_ID= (SELECT id FROM LOGIN_INFO WHERE PROVIDER_ID=${authenticator.loginInfo.providerID}  and PROVIDER_KEY = ${authenticator.loginInfo.providerKey})
            , LAST_USED_DATE_TIME = ${authenticator.lastUsedDateTime}
            , EXPIRATION_DATE_TIME = ${authenticator.expirationDateTime}
            where id = ${authenticator.id}
            """
    ).map(_ => authenticator)
  }

  override def remove(id: String): Future[Unit] = {
    db.run(
      sqlu"""delete from JWT_AUTHENTICATOR
            WHERE ID ='0'"""
    ).map(_ => ())
  }

}