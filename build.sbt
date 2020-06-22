ThisBuild / turbo := true

lazy val root = project
  .in(file("."))
  .settings(
    scalaVersion := "2.13.2",
    organization := "com.avast",
    name := "lambdaconf-demo",
    libraryDependencies ++= Seq(
      "com.avast" %% "sst-bundle-zio-http4s-blaze" % "0.1.43",
      "com.avast" %% "sst-cats-effect" % "0.1.43",
      "com.avast" %% "sst-doobie-hikari-pureconfig" % "0.1.43",
      "com.avast" %% "sst-flyway-pureconfig" % "0.1.43",
      "com.avast" %% "sst-jvm-micrometer" % "0.1.43",
      "com.avast" %% "sst-micrometer-jmx-pureconfig" % "0.1.43",
      "io.circe" %% "circe-core" % "0.13.0",
      "io.circe" %% "circe-generic" % "0.13.0",
      "org.http4s" %% "http4s-circe" % "0.21.4",
      "org.postgresql" % "postgresql" % "42.2.13",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.scalatest" %% "scalatest" % "3.1.2" % Test,
      "com.dimafeng" %% "testcontainers-scala-scalatest" % "0.37.0" % Test
    )
  )
