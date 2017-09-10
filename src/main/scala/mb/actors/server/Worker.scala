package mb.actors.server

import mb.utils.GlobalMessages._
import mb.services.UserService

import akka.actor._

class Worker(userService: UserService) extends Actor {

  /**
    * set up by default state
    */
  var clientUsername: String  = ""
  var clientId: Long          = -1
  var clientRef: ActorRef     = self

  /**
    * loggedIn state
    * @return Receive
    */
  def loggedIn: Receive = {

    case simpleMessage: SimpleMessage => sender ! SimpleMessage( simpleMessage.message + " - Pong" )

  }

  /**
    * default state
    * @return Receive
    */
  override def receive: Receive = {

    case simpleMessage: SimpleMessage =>

      val commandArray = simpleMessage.message.split(" ")

      if(     simpleMessage.message.startsWith("login") && commandArray.length == 2) {

        val tmpClientId = userService.checkAuthDataAndGetId( commandArray(1), commandArray(2) )
        if(tmpClientId.isDefined) {
          clientUsername = commandArray(1)
          clientId = tmpClientId.get
          clientRef = sender
          sender ! SimpleMessage("Welcome \"" + clientUsername + "\"!")
          context.become(loggedIn)
        }
        else sender ! SimpleMessage("Login failed: Check auth data")

      }
      else if(simpleMessage.message.startsWith("create") && commandArray.length == 2) {

        userService.create( commandArray(1), commandArray(2) )
        sender ! SimpleMessage("User created")

      }
      else sender ! SimpleMessage("Please login with \"login <USERNAME> <PASSWORD>\" or create user with \"create <USERNAME> <PASSWORD>\"")

  }

}