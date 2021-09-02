name := """play-framework-starter-package"""
organization := "play-framework-starter"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies ++= Seq(
  jdbc,
  "com.typesafe.play" %% "play-json" % "2.9.2",
  "mysql" % "mysql-connector-java" % "8.0.22",
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "play-framework-starter.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "play-framework-starter.binders._"
