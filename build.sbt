import sbt.Resolver.bintrayRepo
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.packager.docker._

organization in ThisBuild := "less.stupid"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

EclipseKeys.projectFlavor in Global := EclipseProjectFlavor.Java

lazy val `may-thy-knife-chip-and-shatter` = (project in file("."))
  .aggregate(`prices-api`, `prices-impl`,
             `statscentre-api`, `statscentre-impl`,
             `fixtures-api`, `fixtures-impl`)

lazy val `prices-api` = (project in file("prices-api"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok
    )
  )

lazy val `prices-impl` = (project in file("prices-impl"))
  .enablePlugins(LagomJava)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaBroker,
      lagomJavadslTestKit,
      lagomJavadslPubSub,
      lombok,
      strata,
      asynchttpclient,
      jacksonJoda,
      assertJ
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`prices-api`)

lazy val `statscentre-api` = (project in file("statscentre-api"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok
    )
  )

lazy val `statscentre-impl` = (project in file("statscentre-impl"))
  .enablePlugins(LagomJava)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaBroker,
      lagomJavadslTestKit,
      lombok,
      xstream,
      assertJ,
      faker
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`statscentre-api`)

lazy val `fixtures-api` = (project in file("fixtures-api"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok
    )
  )

lazy val `fixtures-impl` = (project in file("fixtures-impl"))
  .enablePlugins(LagomJava)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaBroker,
      lagomJavadslTestKit
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`fixtures-api`)

val slf4j = "org.slf4j" % "slf4j-api" % "1.7.25"
val lombok = "org.projectlombok" % "lombok" % "1.16.10"
val assertJ = "org.assertj" % "assertj-core" % "3.8.0"
val faker = "com.github.javafaker" % "javafaker" % "0.13"
val strata = "com.opengamma.strata" % "strata-collect" % "1.4.2"
val joda = "joda-time" % "joda-time" % "2.9.3"
val jacksonJoda = "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.9.2"
val asynchttpclient = "org.asynchttpclient" % "async-http-client" % "2.0.37"
val xstream = "com.thoughtworks.xstream" % "xstream" % "1.4.9"

def commonSettings: Seq[Setting[_]] = eclipseSettings ++ Seq(
  javacOptions in Compile ++= Seq("-encoding", "UTF-8", "-source", "1.8"),
  javacOptions in(Compile, compile) ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-parameters")
)

lagomCassandraCleanOnStart in ThisBuild := false

