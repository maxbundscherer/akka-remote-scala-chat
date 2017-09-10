package mb.utils

object ClientMessages {

  case class Command(content: String)

}

object ServerMessages {

  case class ServerPushToClient(content: String)

  case class ClientHasLoggedIn(clientUsername: String)
  case class ClientHasLoggedOut(clientUsername: String)
  case class ClientSendBroadcastMessage(content: String, clientUsername: String)
  case class ClientSendPrivateMessage(toUser: String, content: String, clientUsername: String)

}

object GlobalMessages {

  case class SimpleMessage(message: String)

}