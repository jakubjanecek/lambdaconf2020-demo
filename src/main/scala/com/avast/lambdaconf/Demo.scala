package com.avast.lambdaconf

import cats.effect.Resource
import com.avast.sst.bundle.ZioServerApp
import com.avast.sst.doobie.pureconfig.implicits._
import com.avast.sst.flyway.pureconfig.implicits._
import com.avast.sst.http4s.server.pureconfig.implicits._
import com.avast.sst.jvm.pureconfig.implicits._
import com.avast.sst.micrometer.jmx.pureconfig.implicits._
import org.http4s.server.Server
import pureconfig.generic.auto._
import zio._
import zio.interop.catz._

object Demo extends ZioServerApp {

  override def program: Resource[Task, Server[Task]] = {
    for {
      _ <- Resource.liftF(Task.effect(???))
    } yield ???
  }

}
