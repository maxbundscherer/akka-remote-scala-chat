package mb

import mb.actors.client.Supervisor
import mb.utils.ClientMessages

import akka.actor._
import com.typesafe.config.ConfigFactory
import scala.io.StdIn

class MainClient {

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