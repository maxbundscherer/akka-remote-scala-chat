package com.mb.akkaremotechat

import com.mb.akkaremotechat.actors.server.Supervisor
import com.mb.akkaremotechat.services._

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

object MainServer extends App {

  val config = ConfigFactory.load()

  implicit val actorSystem = ActorSystem("serverSystem", config.getConfig("serverConfig").withFallback(config))

  val databaseService   = new DatabaseService()

  new MigrationService(databaseService)

  val userService       = new UserService(databaseService)
  val messageService    = new MessageService(databaseService)

  val supervisor = actorSystem.actorOf( Props( new Supervisor(userService, messageService) ), "supervisor" )

  actorSystem.log.info("Press enter to shutdown actor-system")
  StdIn.readLine()
  actorSystem.terminate()

}
