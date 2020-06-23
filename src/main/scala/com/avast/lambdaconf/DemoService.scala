package com.avast.lambdaconf

import com.avast.lambdaconf.model.{User, UserId}
import doobie.hikari.HikariTransactor
import doobie.implicits._
import io.micrometer.core.instrument.MeterRegistry
import zio.Task
import zio.interop.catz._

trait DemoService {

  def readUser(user: UserId): Task[Option[User]]

}

object DemoService {

  class PostgresDemoService(doobie: HikariTransactor[Task], meterRegistry: MeterRegistry) extends DemoService {
    override def readUser(user: UserId): Task[Option[User]] = {
      for {
        _ <- Task.effect(meterRegistry.counter("requests").increment())
        result <-
          sql"SELECT * FROM users WHERE id = ${user.value}"
            .query[User]
            .option
            .transact(doobie)
      } yield result
    }
  }

}
