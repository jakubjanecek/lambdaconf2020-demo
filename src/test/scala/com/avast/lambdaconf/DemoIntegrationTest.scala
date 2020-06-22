package com.avast.lambdaconf

import cats.effect.Resource
// import com.avast.lambdaconf.model.User
import com.avast.sst.http4s.client.{Http4sBlazeClientConfig, Http4sBlazeClientModule}
import io.circe.generic.auto._
import org.http4s.{Request, Uri}
import zio._
import zio.interop.catz._

import scala.concurrent.ExecutionContext.global

class DemoIntegrationTest extends TestBase {

  test("hello") {
    val resource = for {
      _ <- Demo.program
      client <- Http4sBlazeClientModule.make[Task](Http4sBlazeClientConfig(), global)
      response <- Resource.liftF(client.fetchAs[String](Request[Task]().withUri(Uri.unsafeFromString("http://localhost:8080/hello"))))
    } yield assert(response === "Hello world!")

    unsafeRunToFuture(resource.use(Task.succeed(_)))
  }

  test("server") {
    val resource = for {
      _ <- Demo.program
      client <- Http4sBlazeClientModule.make[Task](Http4sBlazeClientConfig(), global)
      // response <- Resource.liftF(client.fetchAs[User](Request[Task]().withUri(Uri.unsafeFromString("http://localhost:8080/users/1"))))
    } yield fail // assert(response.firstName === "Jakub")

    unsafeRunToFuture(resource.use(Task.succeed(_)))
  }

}
