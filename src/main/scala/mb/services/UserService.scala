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
    * check authData
    * @param username String
    * @param password String
    * @return Boolean
    */
  def checkAuthDataAndGetId(username: String, password: String): Boolean = {

    val entity = Await.result(db.run( userQuery.filter(_.username === username).result.headOption ), Duration.Inf)

    if(entity.isDefined && entity.get.checkPassword(password)) true
    else false
  }

}