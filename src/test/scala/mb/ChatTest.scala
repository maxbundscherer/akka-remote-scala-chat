package mb

import mb.utils.GlobalMessages.SimpleMessage
import mb.actors.server.Supervisor

import akka.actor._
import akka.testkit._

import org.scalatest._

class ChatTest extends TestKit(ActorSystem("clientSystem")) with ImplicitSender with WordSpecLike with Matchers with BaseTest {

  val serverSupervisor = actorSystem.actorOf( Props(new Supervisor(userService)) )

  "serverSupervisor with clean test-db" must {

    "send login-demand if client isn't logged in" in {

      serverSupervisor ! SimpleMessage("test")
      expectMsg(SimpleMessage("Please login with \"login <USERNAME> <PASSWORD>\" or create user with \"create <USERNAME> <PASSWORD>\""))
    }

    "create user" in {

      serverSupervisor ! SimpleMessage("create testUser testPass")
      expectMsg(SimpleMessage("User created"))
    }

    "send login-error if client used wrong authData" in {

      serverSupervisor ! SimpleMessage("login testUser testWrongPass")
      expectMsg(SimpleMessage("Login failed: Check auth data"))
    }

    "send welcome message if client logged in" in {

      serverSupervisor ! SimpleMessage("login testUser testPass")
      expectMsg(SimpleMessage("Welcome \"testUser\"!"))
    }

    "reply with broadcast message if client send broadcast message" in {

      serverSupervisor ! SimpleMessage("testMessage")
      expectMsg(SimpleMessage("[testUser]: testMessage"))
    }

  }
}