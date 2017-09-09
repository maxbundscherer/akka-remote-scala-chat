package mb

import mb.services._

import akka.actor._
import akka.event._
import com.typesafe.config.ConfigFactory
import scala.io.StdIn

object MainServer extends App {

  val config = ConfigFactory.load()

  implicit val actorSystem = ActorSystem("serverSystem", config.getConfig("serverConfig").withFallback(config))
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)

  val databaseService   = new DatabaseService()
  val migrationService  = new MigrationService(databaseService, log)

  log.info("Press enter to shutdown actor-system")
  StdIn.readLine()
  actorSystem.terminate()

}
