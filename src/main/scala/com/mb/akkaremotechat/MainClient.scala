package com.mb.akkaremotechat

import com.mb.akkaremotechat.actors.client.Supervisor
import com.mb.akkaremotechat.utils.ClientMessages

import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.io.StdIn

object MainClient extends App {

  final val EXIT_STRING: String = "exit"

  val config = ConfigFactory.load()

  val actorSystem = ActorSystem("clientSystem", config.getConfig("clientConfig").withFallback(config))

  val supervisor = actorSystem.actorOf( Props(new Supervisor()), "supervisor" )

  var command = ""

  actorSystem.log.info("Enter command (should send to server) or write \"exit\" and press enter")

  do {

    command = StdIn.readLine().trim

    if(!command.equals(EXIT_STRING)) {
      supervisor tell (ClientMessages.Command(command) , ActorRef.noSender)
    }

  } while(!command.equals(EXIT_STRING))

  actorSystem.terminate()

}
