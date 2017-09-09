package mb.utils

trait DatabaseDriver {

  protected val databaseDriver = slick.jdbc.MySQLProfile.api

}
