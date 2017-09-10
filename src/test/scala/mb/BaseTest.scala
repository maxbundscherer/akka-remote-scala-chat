package mb

import mb.services._
import mb.utils.InitBaseline

import akka.actor.ActorSystem

trait BaseTest {

  InitBaseline

  implicit val actorSystem: ActorSystem = InitBaseline.actorSystem

  val userService     = new UserService(InitBaseline.databaseService)
  var messageService  = new MessageService(InitBaseline.databaseService)
}
