package mb

import mb.services.DatabaseService
import mb.utils.InitServer

import akka.actor.ActorSystem

trait BaseServerTest {

  InitServer

  implicit val actorSystem: ActorSystem = InitServer.actorSystem

  val databaseService: DatabaseService  = InitServer.databaseService

}
