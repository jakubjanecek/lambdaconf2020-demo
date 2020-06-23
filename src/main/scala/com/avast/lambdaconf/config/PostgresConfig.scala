package com.avast.lambdaconf.config

import com.avast.sst.doobie.DoobieHikariConfig
import com.avast.sst.doobie.pureconfig.implicits._
import com.avast.sst.flyway.FlywayConfig
import com.avast.sst.flyway.pureconfig.implicits._
import com.avast.sst.jvm.execution.ThreadPoolExecutorConfig
import com.avast.sst.jvm.pureconfig.implicits._
import pureconfig.ConfigReader
import pureconfig.generic.semiauto.deriveReader

final case class PostgresConfig(
    doobie: DoobieHikariConfig,
    boundedConnectExecutor: ThreadPoolExecutorConfig,
    flyway: FlywayConfig
)

object PostgresConfig {

  implicit val configReader: ConfigReader[PostgresConfig] = deriveReader

}
