package com.mb.akkaremotechat.models.db

import com.mb.akkaremotechat.utils.DatabaseDriver
import com.mb.akkaremotechat.models.UserEntity

import slick.relational.RelationalProfile.ColumnOption.Length

trait UserTableEntity extends DatabaseDriver {

  import databaseDriver._

  class Users(tag: slick.lifted.Tag) extends Table[UserEntity](tag, "users") {

    def username = column[String] ("username",  O.PrimaryKey, Length(50))
    def password = column[String] ("password",  Length(500))

    def * = (username, password) <> ((UserEntity.apply _).tupled, UserEntity.unapply)
  }

  protected val userQuery: TableQuery[Users] = TableQuery[Users]
}
