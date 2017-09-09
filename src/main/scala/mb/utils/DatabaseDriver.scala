package mb.utils

trait DatabaseDriver {

  protected final val databaseDriver = slick.jdbc.MySQLProfile.api

}
