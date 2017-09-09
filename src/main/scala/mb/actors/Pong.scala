package mb.actors

import akka.actor.Actor

class Pong extends Actor {

  override def receive: Receive = {

    case string: String => sender ! string + " - Pong"

  }
}
