val scala3Version = "3.1.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "beliefspread",
    organization := "dev.r0bert",
    version := "0.15.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0-M6" % Test,
      "org.apache.commons" % "commons-lang3" % "3.12.0" % Test
    )
  )

ThisBuild / organization := "dev.r0bert"
ThisBuild / organizationName := "Robert Greener"
ThisBuild / organizationHomepage := Some(url("https://r0bert.dev"))

ThisBuild / versionScheme := Some("early-semver")

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://git.sr.ht/~ragreener1/beliefspread"),
    "scm:https://git.sr.ht/~ragreener1/beliefspread"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "ragreener1",
    name = "Robert Greener",
    email = "me@r0bert.dev",
    url = url("https://r0bert.dev")
  )
)

ThisBuild / description := "This is a Scala library which will allow you to model how beliefs an behaviours spread between agents."
ThisBuild / licenses := List(
  "BSD-3-Clause" -> new URL(
    "https://git.sr.ht/~ragreener1/beliefspread/blob/main/LICENSE"
  )
)
ThisBuild / homepage := Some(url("https://git.sr.ht/~ragreener1/beliefspread"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
