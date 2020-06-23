package com.avast.lambdaconf

import java.io.File

import com.dimafeng.testcontainers.{DockerComposeContainer, ExposedService}
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import io.circe.generic.auto._
import org.scalatest.funsuite.AsyncFunSuite
import zio._
import zio.interop.catz._

trait TestBase extends AsyncFunSuite with TestContainerForAll with BootstrapRuntime {

  implicit val runtime: Runtime[ZEnv] = this

  override val containerDef: DockerComposeContainer.Def = DockerComposeContainer.Def(
    new File("docker-compose.yml"),
    List(ExposedService("postgres", 5432))
  )

}
