package com.avast.lambdaconf.model

import io.circe.generic.auto._
import org.http4s.{EntityDecoder, EntityEncoder, circe}
import zio.Task
import zio.interop.catz._

final case class User(id: UserId, firstName: String, lastName: String, admin: Boolean)

object User {

  implicit val encoder: EntityEncoder[Task, User] = circe.jsonEncoderOf[Task, User]
  implicit val decoder: EntityDecoder[Task, User] = circe.jsonOf[Task, User]

}
