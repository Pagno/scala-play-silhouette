package models.daos

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait DAO extends HasDatabaseConfigProvider[JdbcProfile]