package mb.models

import org.mindrot.jbcrypt.BCrypt

case class UserEntity(id: Option[Long], username: String, password: String) {

  def withHashedPassword: UserEntity = UserEntity(id, username, BCrypt.hashpw(password, BCrypt.gensalt()))
  def checkPassword(password: String): Boolean = BCrypt.checkpw(password, this.password)

}