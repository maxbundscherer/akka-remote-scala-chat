package mb.actors.server

import mb.utils.GlobalMessages._
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
  }

}