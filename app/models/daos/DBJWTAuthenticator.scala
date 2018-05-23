package models.daos

import org.joda.time.DateTime

case class DBJWTAuthenticator(id: String,
                              providerId: String,
                              providerKey: String,
                              lastUsedDateTime: DateTime,
                              expirationDateTime: DateTime)
