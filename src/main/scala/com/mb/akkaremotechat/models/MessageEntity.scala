package com.mb.akkaremotechat.models

case class MessageEntity(id: Option[Long], fromUser: String, toUser: String, content: String, hasRead: Boolean)