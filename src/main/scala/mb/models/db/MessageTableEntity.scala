package mb.models.db

import mb.utils.DatabaseDriver
import mb.models.MessageEntity

import slick.relational.RelationalProfile.ColumnOption.Length

trait MessageTableEntity extends DatabaseDriver {

  import databaseDriver._

  class Messages(tag: slick.lifted.Tag) extends Table[MessageEntity](tag, "messages") with UserTableEntity {

    def id        = column[Long]    ("id", O.PrimaryKey, O.AutoInc)
    def fromUser  = column[String]  ("fromUser", Length(50))
    def toUser    = column[String]  ("toUser", Length(50))
    def content   = column[String]  ("content", Length(5000))
    def hasRead   = column[Boolean] ("hasRead", O.Default(false))

    def fkFromUser = foreignKey("FromUser_FK", fromUser,  userQuery)(_.username, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Cascade)
    def fkToUser   = foreignKey("ToUser_FK",   toUser,    userQuery)(_.username, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Cascade)

    def * = (id.?, fromUser, toUser, content, hasRead) <> ((MessageEntity.apply _).tupled, MessageEntity.unapply)
  }

  protected val messageQuery: TableQuery[Messages] = TableQuery[Messages]
}