package mb.actors.server

import mb.utils.GlobalMessages._
import mb.utils.ServerMessages
import mb.services.{MessageService, UserService}

import akka.actor._
import scala.collection.mutable

class Supervisor(userService: UserService, messageService: MessageService) extends Actor {

  /**
    * ActorRef = RemoteRef
    * ActorRef = WorkerRef
    */
  var clientMap: mutable.HashMap[ActorRef, ActorRef] = new mutable.HashMap()

  /**
    * String = Username
    * ActorRef = WorkerRef
    */
  var usernameMap: mutable.HashMap[String, ActorRef] = new mutable.HashMap()

  /**
    * default state
    * @return Receive
    */
  override def receive: Receive = {

    case simpleMessage: SimpleMessage =>

      var workerRef = clientMap.get(sender)

      if(workerRef.isEmpty) {
        workerRef = Option( context.actorOf( Props(new Worker(userService) ) ) )
        clientMap.put( sender, workerRef.get )
      }

      workerRef.get forward simpleMessage

    case clientHasLoggedIn: ServerMessages.ClientHasLoggedIn =>

      usernameMap.put( clientHasLoggedIn.clientUsername, sender )
      sender ! ServerMessages.ServerPushToClient("Welcome \"" + clientHasLoggedIn.clientUsername + "\"!")

      val unreadMessages = getUnreadMessagesFromUserAsString(clientHasLoggedIn.clientUsername)
      if(unreadMessages.isDefined) sender ! ServerMessages.ServerPushToClient(unreadMessages.get)

    case clientHasLoggedOut: ServerMessages.ClientHasLoggedOut =>

      usernameMap.remove(clientHasLoggedOut.clientUsername)

    case clientSendBroadcastMessage: ServerMessages.ClientSendBroadcastMessage =>

      usernameMap.values.foreach(c => {

        val msg = "[" + clientSendBroadcastMessage.clientUsername + "]: " + clientSendBroadcastMessage.content
        c ! ServerMessages.ServerPushToClient(msg)

      })

    case clientSendPrivateMessage: ServerMessages.ClientSendPrivateMessage =>

      if(userService.existsUser(clientSendPrivateMessage.toUser)) {

        messageService.writePrivateMessage(clientSendPrivateMessage.clientUsername, clientSendPrivateMessage.toUser, clientSendPrivateMessage.content)
        sender ! ServerMessages.ServerPushToClient("Private message send")

        val onlineUserRef = usernameMap.get(clientSendPrivateMessage.toUser)
        if(onlineUserRef.isDefined) {
          onlineUserRef.get ! ServerMessages.ServerPushToClient(getUnreadMessagesFromUserAsString(clientSendPrivateMessage.clientUsername).get)
        }

      }
      else sender ! ServerMessages.ServerPushToClient("Error: User not found")

  }

  /**
    * get all unread messages from user as text
    * @param username String
    * @return Option(String) with text / None if there are no new messages
    */
  def getUnreadMessagesFromUserAsString(username: String): Option[String] = {

    var messages: String = ""
    messageService.getUnreadMessagesFromUser(username).foreach(m => messages += "[" + m.fromUser + "] (PM): " + m.content)

    if(messages.equals("")) return None
    Option(messages)
  }

}