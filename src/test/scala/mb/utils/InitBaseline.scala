package mb.utils

import mb.services._

import akka.actor.ActorSystem

object InitBaseline {

  implicit val actorSystem = ActorSystem()

  val databaseService = new DatabaseService

  new MigrationService(databaseService)

}
