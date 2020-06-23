package com.avast.lambdaconf.config

import com.avast.sst.http4s.server.Http4sBlazeServerConfig
import com.avast.sst.http4s.server.pureconfig.implicits._
import com.avast.sst.micrometer.jmx.MicrometerJmxConfig
import com.avast.sst.micrometer.jmx.pureconfig.implicits._
import pureconfig.ConfigReader
import pureconfig.generic.semiauto.deriveReader

final case class Configuration(server: Http4sBlazeServerConfig, jmx: MicrometerJmxConfig, postgres: PostgresConfig)

object Configuration {

  implicit val configReader: ConfigReader[Configuration] = deriveReader

}
