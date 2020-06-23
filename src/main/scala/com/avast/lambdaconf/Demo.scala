package com.avast.lambdaconf

import cats.effect.Resource
import com.avast.lambdaconf.DemoService.PostgresDemoService
import com.avast.lambdaconf.config.Configuration
import com.avast.lambdaconf.module.{HttpModule, PostgresModule}
import com.avast.sst.bundle.ZioServerApp
import com.avast.sst.doobie.pureconfig.implicits._
import com.avast.sst.flyway.FlywayModule
import com.avast.sst.flyway.pureconfig.implicits._
import com.avast.sst.http4s.server.Http4sBlazeServerModule
import com.avast.sst.http4s.server.pureconfig.implicits._
import com.avast.sst.jvm.execution.ExecutorModule
import com.avast.sst.jvm.micrometer.MicrometerJvmModule
import com.avast.sst.jvm.pureconfig.implicits._
import com.avast.sst.micrometer.jmx.MicrometerJmxModule
import com.avast.sst.micrometer.jmx.pureconfig.implicits._
import com.avast.sst.pureconfig.PureConfigModule
import org.http4s.server.Server
import pureconfig.ConfigSource
import zio._
import zio.interop.catz._
import zio.interop.catz.implicits._

object Demo extends ZioServerApp {

  override def program: Resource[Task, Server[Task]] = {
    for {
      configuration <- Resource.liftF(PureConfigModule.makeOrRaise[Task, Configuration](ConfigSource.default))

      executorModule <- ExecutorModule.makeFromExecutionContext[Task](runtime.platform.executor.asEC)

      meterRegistry <- MicrometerJmxModule.make[Task](configuration.jmx)
      _ <- Resource.liftF(MicrometerJvmModule.make[Task](meterRegistry))

      xa <- PostgresModule.make(configuration.postgres, executorModule, meterRegistry)

      flyway <- Resource.liftF(FlywayModule.make[Task](xa.kernel, configuration.postgres.flyway))
      _ <- Resource.liftF(Task.effect(flyway.migrate()))

      httpModule = new HttpModule(new PostgresDemoService(xa, meterRegistry))

      server <- Http4sBlazeServerModule.make[Task](configuration.server, httpModule.router, runtime.platform.executor.asEC)
    } yield server
  }

}
