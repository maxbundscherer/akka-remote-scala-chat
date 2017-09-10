package mb

import mb.utils.GlobalMessages.SimpleMessage
import mb.actors.server.Supervisor

import akka.actor._
import akka.testkit._

import org.scalatest._

class ChatTest extends TestKit(ActorSystem("clientSystem")) with ImplicitSender with WordSpecLike with Matchers with BaseTest {

  val serverSupervisor = actorSystem.actorOf( Props(new Supervisor(userService)) )

  "serverSupervisor" must {

    "send back login-demand if client isn't logged in" in {

      serverSupervisor ! SimpleMessage("test")
      expectMsg(SimpleMessage("Please login with \"login <USERNAME> <PASSWORD>\" or create user with \"create <USERNAME> <PASSWORD>\""))
    }

  }
}