package com.avast.lambdaconf.module

import cats.effect.Resource
import com.avast.lambdaconf.config.PostgresConfig
import com.avast.sst.doobie.DoobieHikariModule
import com.avast.sst.jvm.execution.ConfigurableThreadFactory.Config
import com.avast.sst.jvm.execution.{ConfigurableThreadFactory, ExecutorModule}
import com.zaxxer.hikari.metrics.micrometer.MicrometerMetricsTrackerFactory
import doobie.hikari.HikariTransactor
import io.micrometer.core.instrument.MeterRegistry
import zio.Task
import zio.interop.catz._

import scala.concurrent.ExecutionContext

object PostgresModule {

  def make(
      postgresConfig: PostgresConfig,
      executorModule: ExecutorModule[Task],
      meterRegistry: MeterRegistry
  ): Resource[Task, HikariTransactor[Task]] = {
    for {
      boundedConnectExecutor <-
        executorModule
          .makeThreadPoolExecutor(postgresConfig.boundedConnectExecutor, new ConfigurableThreadFactory(Config(Some("hikari-connect-%02d"))))
          .map(ExecutionContext.fromExecutorService)
      hikariMetrics = new MicrometerMetricsTrackerFactory(meterRegistry)
      doobieTransactor <-
        DoobieHikariModule.make[Task](postgresConfig.doobie, boundedConnectExecutor, executorModule.blocker, Some(hikariMetrics))
    } yield doobieTransactor
  }

}
