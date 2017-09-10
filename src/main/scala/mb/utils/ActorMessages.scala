package mb.utils

object ClientMessages {

  case class Command(content: String)

}

object ServerMessages {

}

object GlobalMessages {

  case class SimpleMessage(message: String)

}