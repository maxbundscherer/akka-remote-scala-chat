package mb.models.db

import mb.utils.DatabaseDriver
import mb.models.UserEntity

import slick.relational.RelationalProfile.ColumnOption.Length

trait UserTableEntity extends DatabaseDriver {

  import databaseDriver._

  class Users(tag: slick.lifted.Tag) extends Table[UserEntity](tag, "users") {

    def id       = column[Long]   ("id",        O.PrimaryKey, O.AutoInc)
    def username = column[String] ("username",  Length(50), O.Unique)
    def password = column[String] ("password",  Length(500))

    def * = (id.?, username, password) <> ((UserEntity.apply _).tupled, UserEntity.unapply)
  }

  protected val userQuery: TableQuery[Users] = TableQuery[Users]
}
