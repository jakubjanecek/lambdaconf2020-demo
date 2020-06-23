package com.avast.lambdaconf.module

import com.avast.lambdaconf.DemoService
import com.avast.lambdaconf.model.UserId
import com.avast.sst.http4s.server.Http4sRouting
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpApp, HttpRoutes}
import zio.Task
import zio.interop.catz._

class HttpModule(demoService: DemoService) extends Http4sDsl[Task] {

  private val routes = HttpRoutes.of[Task] {
    case GET -> Root / "hello" => Ok("Hello world!")
    case GET -> Root / "users" / LongVar(userId) =>
      demoService
        .readUser(UserId(userId))
        .flatMap(_.map(Ok(_)).getOrElse(NotFound()))

  }

  val router: HttpApp[Task] = Http4sRouting.make(routes)

}
