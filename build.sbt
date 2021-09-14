name := """play-framework-starter-package"""
organization := "play-framework-starter"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies ++= Seq(
  jdbc,
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.3",
  "com.typesafe.play" %% "play-json" % "2.9.2",
  "mysql" % "mysql-connector-java" % "8.0.25",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "org.mindrot" % "jbcrypt" % "0.4",

  "org.scalikejdbc" %% "scalikejdbc" % "3.5.0",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.5.0",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5",
  "org.scalikejdbc" %% "scalikejdbc-test" % "3.5.0" % "test",
)
libraryDependencies += evolutions

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "play-framework-starter.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "play-framework-starter.binders._"
