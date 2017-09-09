package mb.services

import mb.utils.DatabaseDriver

import slick.jdbc.MySQLProfile

class DatabaseService extends DatabaseDriver {

  import databaseDriver._

  final val db: MySQLProfile.backend.Database = Database.forConfig("databaseConfig")

}