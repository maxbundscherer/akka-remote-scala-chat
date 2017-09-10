package mb.actors.server

import mb.utils.GlobalMessages._
import mb.utils.ServerMessages
import mb.services.UserService

import akka.actor._
import scala.collection.mutable

class Supervisor(userService: UserService) extends Actor {

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

      usernameMap.put( clientHasLoggedIn.username, sender )
      sender ! ServerMessages.ServerPushToClient("Welcome \"" + clientHasLoggedIn.username + "\"!")

    case clientSendBroadcastMessage: ServerMessages.ClientSendBroadcastMessage =>

      usernameMap.values.foreach(c => {

        val msg = "[" + clientSendBroadcastMessage.username + "]: " + clientSendBroadcastMessage.content
        c ! ServerMessages.ServerPushToClient(msg)

      })
  }

}