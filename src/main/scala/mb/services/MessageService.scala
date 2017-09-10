package mb.services

import mb.utils.DatabaseDriver
import mb.models.MessageEntity
import mb.models.db.MessageTableEntity

import scala.concurrent.duration.Duration
import scala.concurrent.Await

class MessageService(databaseService: DatabaseService) extends MessageTableEntity with DatabaseDriver {

  import databaseDriver._
  import databaseService._

  //TODO: Exception handling and async programming

  /**
    * write private message to user
    * @param fromUser String
    * @param toUser String
    * @param content String
    */
  def writePrivateMessage(fromUser: String, toUser: String, content: String): Unit = Await.result( db.run( messageQuery += MessageEntity(None, fromUser, toUser, content, hasRead = false) ), Duration.Inf )

  /**
    * get unread messages from user
    * @param username String
    * @return Seq[MessageEntity]
    */
  def getUnreadMessagesFromUser(username: String): Seq[MessageEntity] = {

    val items: Seq[MessageEntity] = Await.result( db.run( messageQuery.filter(_.toUser === username).filter(_.hasRead === false).result ), Duration.Inf )
    db.run( messageQuery.filter(_.toUser === username).filter(_.hasRead === false).map(x => x.hasRead).update(true) )
    items
  }

  /**
    * get the count of the unread messages from user
    * @param username String
    * @return Int
    */
  def getCountUnreadMessagesFromUser(username: String): Int = {

    val items: Seq[MessageEntity] = Await.result( db.run( messageQuery.filter(_.toUser === username).filter(_.hasRead === false).result ), Duration.Inf )
    items.size
  }

}
