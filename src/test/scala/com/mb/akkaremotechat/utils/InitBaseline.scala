package com.mb.akkaremotechat.utils

import com.mb.akkaremotechat.services._

import akka.actor.ActorSystem

object InitBaseline {

  implicit val actorSystem = ActorSystem()

  val databaseService = new DatabaseService

  new MigrationService(databaseService)

}
