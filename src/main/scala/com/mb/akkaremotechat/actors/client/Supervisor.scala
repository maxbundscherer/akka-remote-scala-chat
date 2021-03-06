package com.mb.akkaremotechat.actors.client

import com.mb.akkaremotechat.utils.GlobalMessages._
import com.mb.akkaremotechat.utils.ClientMessages

import akka.actor._

class Supervisor extends Actor with ActorLogging {

  private val serverSupervisor = context.actorSelection("akka.tcp://serverSystem@127.0.0.1:5150/user/supervisor")

  override def receive: Receive = {

    case command: ClientMessages.Command =>

      serverSupervisor ! SimpleMessage(command.content)

    case simpleMessage: SimpleMessage =>

      log.info(simpleMessage.message)

  }
}