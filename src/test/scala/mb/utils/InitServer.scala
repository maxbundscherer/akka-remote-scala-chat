package mb.utils

import mb.services._

import akka.actor.ActorSystem

object InitServer {

  implicit val actorSystem = ActorSystem()

  val databaseService = new DatabaseService

  new MigrationService(databaseService)

}
