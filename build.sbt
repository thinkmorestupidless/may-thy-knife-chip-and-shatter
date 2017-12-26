import sbt.Resolver.bintrayRepo
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.packager.docker._

organization in ThisBuild := "less.stupid"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

EclipseKeys.projectFlavor in Global := EclipseProjectFlavor.Java

lazy val `may-thy-knife-chip-and-shatter` = (project in file("."))
  .aggregate(`betfair-api`, `betfair-impl`,
             `fixture-api`, `fixture-impl`)

/*lazy val `betfair-models` = (project in file("betfair-models"))
  .settings(
    scalacOptions in Compile ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlog-reflective-calls", "-Xlint"),
    javacOptions in Compile ++= Seq("-Xlint:unchecked", "-Xlint:deprecation"),
    javacOptions in doc in Compile := Seq("-Xdoclint:none"),
    // disable parallel tests
    parallelExecution in Test := false,
    licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0"))),
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.5",
      "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.9.2",
      "org.projectlombok" % "lombok" % "1.16.10",
      "joda-time" % "joda-time" % "2.9.3" % "compile",
      "junit" % "junit" % "4.12" % "test",
      "com.novocode" % "junit-interface" % "0.10" % "test"
    )
  )

lazy val `betfair-client` = (project in file("betfair-client"))
  .settings(
    scalacOptions in Compile ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlog-reflective-calls", "-Xlint"),
    javacOptions in Compile ++= Seq("-Xlint:unchecked", "-Xlint:deprecation"),
    javacOptions in doc in Compile := Seq("-Xdoclint:none"),
    // disable parallel tests
    parallelExecution in Test := false,
    licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0"))),
    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-simple" % "1.7.25",
      "com.opengamma.strata" % "strata-collect" % "1.4.2",
      "com.google.guava" % "guava" % "23.6-jre",
      "com.typesafe" % "config" % "1.3.2",
      "com.typesafe.akka" %% "akka-stream" % "2.5.8",
      //"com.google.inject" % "guice" % "4.1.0",
      "junit" % "junit" % "4.12" % "test",
      "com.novocode" % "junit-interface" % "0.10" % "test"
    )
  )
  .dependsOn(`betfair-models`)

lazy val `betfair-client-async-http` = (project in file("betfair-client-async-http"))
  .settings(
    scalacOptions in Compile ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlog-reflective-calls", "-Xlint"),
    javacOptions in Compile ++= Seq("-Xlint:unchecked", "-Xlint:deprecation"),
    javacOptions in doc in Compile := Seq("-Xdoclint:none"),
    // disable parallel tests
    parallelExecution in Test := false,
    licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0"))),
    libraryDependencies ++= Seq(
      "org.asynchttpclient" % "async-http-client" % "2.0.37",
      "junit" % "junit" % "4.12" % "test",
      "com.novocode" % "junit-interface" % "0.10" % "test"
    )
  )
  .dependsOn(`betfair-client`)*/

lazy val `betfair-api` = (project in file("betfair-api"))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok
    )
  )

lazy val `betfair-impl` = (project in file("betfair-impl"))
  .enablePlugins(LagomJava)
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaBroker,
      lagomJavadslTestKit,
      lombok,
      strata,
      joda,
      jacksonJoda,
      asynchttpclient,
      assertJ
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`betfair-api`)

lazy val `fixture-api` = (project in file("fixture-api"))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok
    )
  )

lazy val `fixture-impl` = (project in file("fixture-impl"))
  .enablePlugins(LagomJava)
  .settings(common: _*)
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
  .dependsOn(`fixture-api`)

val lombok = "org.projectlombok" % "lombok" % "1.16.10"
val assertJ = "org.assertj" % "assertj-core" % "3.8.0"
val faker = "com.github.javafaker" % "javafaker" % "0.13"
val strata = "com.opengamma.strata" % "strata-collect" % "1.4.2"
val joda = "joda-time" % "joda-time" % "2.9.3"
val jacksonJoda = "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.9.2"
val asynchttpclient = "org.asynchttpclient" % "async-http-client" % "2.0.37"

def common = Seq(
  javacOptions in compile += "-parameters"
)

lagomCassandraCleanOnStart in ThisBuild := false

