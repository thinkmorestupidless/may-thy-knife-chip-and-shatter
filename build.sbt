import sbt.Resolver.bintrayRepo
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.packager.docker._

organization in ThisBuild := "less.stupid"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

EclipseKeys.projectFlavor in Global := EclipseProjectFlavor.Java

lazy val `may-thy-knife-chip-and-shatter` = (project in file("."))
  .aggregate(`utils`, `betfair-akka`,
             `prices-api`, `prices-impl`,
             `statscentre-api`, `statscentre-impl`)

lazy val utils = (project in file("utils"))
  .settings(commonSettings: _*)
  .settings(
    javacOptions in doc in Compile := Seq("-Xdoclint:none"),
    // disable parallel tests
    parallelExecution in Test := false,
    licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0"))),
    libraryDependencies ++= Seq(
      akkaActor
    )
  )

lazy val `betfair-akka` = (project in file("betfair-akka"))
  .settings(commonSettings: _*)
  .settings(
    javacOptions in doc in Compile := Seq("-Xdoclint:none"),
    // disable parallel tests
    parallelExecution in Test := false,
    licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0"))),
    libraryDependencies ++= Seq(
      akkaActor,
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.5",
      "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.9.2",
      slf4j,
      lombok,
      strata,
      joda,
      jacksonJoda,
      asynchttpclient,
      "junit" % "junit" % "4.12" % "test",
      assertJ,
      "com.novocode" % "junit-interface" % "0.10" % "test"
    )
  )

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
      assertJ
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(utils, `betfair-akka`, `prices-api`)

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
      assertJ,
      faker
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`statscentre-api`)

val akkaActor = "com.typesafe.akka" %% "akka-actor" % "2.5.8"
val slf4j = "org.slf4j" % "slf4j-api" % "1.7.25"
val lombok = "org.projectlombok" % "lombok" % "1.16.10"
val assertJ = "org.assertj" % "assertj-core" % "3.8.0"
val faker = "com.github.javafaker" % "javafaker" % "0.13"
val strata = "com.opengamma.strata" % "strata-collect" % "1.4.2"
val joda = "joda-time" % "joda-time" % "2.9.3"
val jacksonJoda = "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.9.2"
val asynchttpclient = "org.asynchttpclient" % "async-http-client" % "2.0.37"
val scalajava8compat = "org.scala-lang.modules" %% "scala-java8-compat" % "0.5.0"

def commonSettings: Seq[Setting[_]] = eclipseSettings ++ Seq(
  javacOptions in Compile ++= Seq("-encoding", "UTF-8", "-source", "1.8"),
  javacOptions in(Compile, compile) ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-parameters")
)

lagomCassandraCleanOnStart in ThisBuild := false

