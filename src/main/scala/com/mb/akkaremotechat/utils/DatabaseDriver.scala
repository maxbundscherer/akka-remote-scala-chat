package com.mb.akkaremotechat.utils

trait DatabaseDriver {

  protected val databaseDriver = slick.jdbc.MySQLProfile.api

}
