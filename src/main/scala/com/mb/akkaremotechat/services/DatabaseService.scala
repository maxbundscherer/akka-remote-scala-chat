package com.mb.akkaremotechat.services

import com.mb.akkaremotechat.utils.DatabaseDriver

import slick.jdbc.MySQLProfile

class DatabaseService extends DatabaseDriver {

  import databaseDriver._

  val db: MySQLProfile.backend.Database = Database.forConfig("databaseConfig")

}