ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "Papaya"
  )


libraryDependencies += "org.typelevel" %% "cats-effect" % "3.4.8"

// available for 2.12, 2.13, 3.2
libraryDependencies += "co.fs2" %% "fs2-core" % "3.6.1"

// optional I/O library
libraryDependencies += "co.fs2" %% "fs2-io" % "3.6.1"

// optional reactive streams interop
libraryDependencies += "co.fs2" %% "fs2-reactive-streams" % "3.6.1"

// optional scodec interop
libraryDependencies += "co.fs2" %% "fs2-scodec" % "3.6.1"

libraryDependencies += "io.github.paoloboni" %% "binance-scala-client" % "1.6.1"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "org.postgresql" % "postgresql" % "42.5.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
)

libraryDependencies += "com.typesafe" % "config" % "1.4.2"