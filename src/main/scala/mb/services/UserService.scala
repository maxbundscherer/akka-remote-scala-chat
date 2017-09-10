package mb.services

import mb.utils.DatabaseDriver
import mb.models.UserEntity
import mb.models.db.UserTableEntity

import scala.concurrent.duration.Duration
import scala.concurrent.Await

class UserService(databaseService: DatabaseService) extends UserTableEntity with DatabaseDriver {

  import databaseDriver._
  import databaseService._

  //TODO: Exception handling and async programming

  /**
    * create user
    * @param username String
    * @param password String
    */
  def create(username: String, password: String): Unit = Await.result( db.run( userQuery += UserEntity(None, username, password).withHashedPassword ), Duration.Inf )

  /**
    * check authData and return userId
    * @param username String
    * @param password String
    * @return Option(None) if authData incorrect / Option(Long) with userId if authData correct
    */
  def checkAuthDataAndGetId(username: String, password: String): Option[Long] = {

    val entity = Await.result(db.run( userQuery.filter(_.username === username).result.headOption ), Duration.Inf)

    if(entity.isDefined && entity.get.checkPassword(password)) Option(entity.get.id.get)
    else None
  }

}