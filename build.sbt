val scala3Version = "3.1.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Belief Spread",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0-M6" % Test,
      "org.apache.commons" % "commons-lang3" % "3.12.0" % Test
    )
  )
