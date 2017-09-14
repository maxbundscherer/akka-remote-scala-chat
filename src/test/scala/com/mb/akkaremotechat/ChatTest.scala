package com.mb.akkaremotechat

import com.mb.akkaremotechat.actors.server.Supervisor
import com.mb.akkaremotechat.utils.GlobalMessages.SimpleMessage

import akka.actor._
import akka.testkit._
import org.scalatest._

class ChatTest extends TestKit(ActorSystem("clientSystem")) with ImplicitSender with WordSpecLike with Matchers with BaseTest {

  val serverSupervisor = actorSystem.actorOf( Props(new Supervisor(userService, messageService)) )

  "serverSupervisor with clean test-db" must {

    "reply with login-demand if client isn't logged in" in {

      serverSupervisor ! SimpleMessage("test")
      expectMsg(SimpleMessage("Please login with \"login <USERNAME> <PASSWORD>\" or create user with \"create <USERNAME> <PASSWORD>\""))
    }

    "create user testUser" in {

      serverSupervisor ! SimpleMessage("create testUser testPass")
      expectMsg(SimpleMessage("User created"))
    }

    "create user testUser2" in {

      serverSupervisor ! SimpleMessage("create testUser2 testPass")
      expectMsg(SimpleMessage("User created"))
    }

    "reply with login-error if client used wrong authData" in {

      serverSupervisor ! SimpleMessage("login testUser testWrongPass")
      expectMsg(SimpleMessage("Login failed: Check auth data"))
    }

    "reply with welcome message if testUser logged in" in {

      serverSupervisor ! SimpleMessage("login testUser testPass")
      expectMsg(SimpleMessage("Welcome \"testUser\"!"))
    }

    "reply with broadcast message if client sends broadcast message" in {

      serverSupervisor ! SimpleMessage("testMessage")
      expectMsg(SimpleMessage("[testUser]: testMessage"))
    }

    "reply with error message if client sends unknown command" in {

      serverSupervisor ! SimpleMessage("-wewqe test")
      expectMsg(SimpleMessage("Error: Unknown command"))
    }

    "reply with error message if client sends private message to unknown user" in {

      serverSupervisor ! SimpleMessage("-p unknowUser test")
      expectMsg(SimpleMessage("Error: User not found"))
    }

    "reply with own message if client sends private message to itself" in {

      serverSupervisor ! SimpleMessage("-p testUser testMessage")
      expectMsg(SimpleMessage("Private message send"))
      expectMsg(SimpleMessage("[testUser] (PM): testMessage"))
    }

    "reply with success-message if client sends private message to offline client" in {

      serverSupervisor ! SimpleMessage("-p testUser2 testMessage")
      expectMsg(SimpleMessage("Private message send"))
    }

    "reply with leave-message if client logs out" in {

      serverSupervisor ! SimpleMessage("-logout")
      expectMsg(SimpleMessage("Bye bye"))
    }

    "reply with welcome message and new messages if testUser2 logged in" in {

      serverSupervisor ! SimpleMessage("login testUser2 testPass")
      expectMsg(SimpleMessage("Welcome \"testUser2\"!"))
      expectMsg(SimpleMessage("[testUser] (PM): testMessage"))
    }

  }
}