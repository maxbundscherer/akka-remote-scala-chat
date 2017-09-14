package com.mb.akkaremotechat

import com.mb.akkaremotechat.services._
import com.mb.akkaremotechat.utils.InitBaseline

import akka.actor.ActorSystem

trait BaseTest {

  InitBaseline

  implicit val actorSystem: ActorSystem = InitBaseline.actorSystem

  val userService     = new UserService(InitBaseline.databaseService)
  var messageService  = new MessageService(InitBaseline.databaseService)
}
