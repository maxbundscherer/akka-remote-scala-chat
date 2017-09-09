package mb

import mb.actors.Pong

import akka.actor._
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{Matchers, WordSpecLike}

class PongActor extends TestKit(ActorSystem("system")) with ImplicitSender with WordSpecLike with Matchers {

  val pongActor: ActorRef = system.actorOf( Props(new Pong), "pongActor" )

  "An pong actor" must {

    "reply with modified message" in {
      pongActor ! "Hello World"
      expectMsg("Hello World - Pong")
    }

  }

}
