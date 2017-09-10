package mb.services

import mb.utils.DatabaseDriver
import mb.models.db._

import akka.actor.ActorSystem
import slick.jdbc.meta.MTable
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MigrationService(databaseService: DatabaseService)(implicit val actorSystem: ActorSystem) extends DatabaseDriver with UserTableEntity with MessageTableEntity {

  import databaseDriver._
  import actorSystem._

  log.info("Start migration service")

  private val allTables = Await.result(databaseService.db.run(MTable.getTables("")), Duration.Inf)

  if ( !allTables.exists(_.name.name == userQuery.baseTableRow.tableName) ) {
    log.info("Create " + userQuery.baseTableRow.tableName)
    databaseService.db.run(userQuery.schema.create)
  }

  if ( !allTables.exists(_.name.name == messageQuery.baseTableRow.tableName) ) {
    log.info("Create " + messageQuery.baseTableRow.tableName)
    databaseService.db.run(messageQuery.schema.create)
  }

  log.info("End migration service")

}
