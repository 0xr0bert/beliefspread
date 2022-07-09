val scala3Version = "3.1.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "beliefspread",
    organization := "dev.r0bert",
    version := "0.14.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0-M6" % Test,
      "org.apache.commons" % "commons-lang3" % "3.12.0" % Test
    )
  )
