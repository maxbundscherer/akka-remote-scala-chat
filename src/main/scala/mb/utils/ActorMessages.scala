package mb.utils

object ClientMessages {

  case class Command(content: String)

}

object ServerMessages {

  case class ServerPushToClient(content: String)
  case class ClientSendBroadcastMessage(content: String, username: String)

  case class ClientHasLoggedIn(username: String)

}

object GlobalMessages {

  case class SimpleMessage(message: String)

}