package mb.actors.server

import mb.utils.GlobalMessages._
import mb.utils.ServerMessages
import mb.services.UserService

import akka.actor._

class Worker(userService: UserService) extends Actor {

  /**
    * set up by default state
    */
  var clientUsername: String  = ""
  var clientRef: ActorRef     = self

  /**
    * loggedIn state
    * @return Receive
    */
  def loggedIn: Receive = {

    case simpleMessage: SimpleMessage =>

      val commandArray = simpleMessage.message.split(" ")

      /**
        * send private message
        */
      if(simpleMessage.message.startsWith("-p") && commandArray.length > 2) {

        val content = simpleMessage.message.substring( simpleMessage.message.indexOf(" ", 3) + 1 )
        context.parent ! ServerMessages.ClientSendPrivateMessage( commandArray(1), content, clientUsername )
      }

      /**
        * logout
        */
      else if(simpleMessage.message.equals("-logout")) {

        context.parent ! ServerMessages.ClientHasLoggedOut(clientUsername)
        clientUsername = ""
        clientRef = self
        sender ! SimpleMessage("Bye bye")
        context.unbecome()

      }

      /**
        * broadcast message or unknown command
        */
      else {

        val message = simpleMessage.message

        if(!message.startsWith("-")) context.parent ! ServerMessages.ClientSendBroadcastMessage(message, clientUsername)
        else sender ! SimpleMessage("Error: Unknown command")
      }

    case serverPushToClient: ServerMessages.ServerPushToClient =>

      clientRef ! SimpleMessage(serverPushToClient.content)
  }

  /**
    * default state
    * @return Receive
    */
  override def receive: Receive = {

    case simpleMessage: SimpleMessage =>

      val commandArray = simpleMessage.message.split(" ")

      if(     simpleMessage.message.startsWith("login") && commandArray.length == 3) {

        if( userService.checkAuthData(commandArray(1), commandArray(2)) ) {
          clientUsername = commandArray(1)
          clientRef = sender
          context.parent ! ServerMessages.ClientHasLoggedIn(clientUsername)
          context.become(loggedIn)
        }
        else sender ! SimpleMessage("Login failed: Check auth data")

      }
      else if(simpleMessage.message.startsWith("create") && commandArray.length == 3) {

        userService.create( commandArray(1), commandArray(2) )
        sender ! SimpleMessage("User created")

      }
      else sender ! SimpleMessage("Please login with \"login <USERNAME> <PASSWORD>\" or create user with \"create <USERNAME> <PASSWORD>\"")

  }

}