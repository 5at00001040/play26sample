name := """play26sample"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test


libraryDependencies += "com.h2database" % "h2" % "1.4.196"

libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0"
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.2.1"

// silhouette ----------------
libraryDependencies += ehcache
libraryDependencies += "com.mohiva" %% "play-silhouette" % "5.0.0"
libraryDependencies += "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.0"
libraryDependencies += "com.mohiva" %% "play-silhouette-persistence" % "5.0.0"
libraryDependencies += "com.mohiva" %% "play-silhouette-crypto-jca" % "5.0.0"
libraryDependencies += "org.webjars" %% "webjars-play" % "2.6.1"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.7-1" exclude("org.webjars", "jquery")
libraryDependencies += "org.webjars" % "jquery" % "3.2.1"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"
libraryDependencies += "com.iheart" %% "ficus" % "1.4.1"
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "6.0.1"
libraryDependencies += "com.typesafe.play" %% "play-mailer-guice" % "6.0.1"
libraryDependencies += "com.enragedginger" %% "akka-quartz-scheduler" % "1.6.1-akka-2.5.x"
libraryDependencies += "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3"
libraryDependencies += "com.mohiva" %% "play-silhouette-testkit" % "5.0.0" % "test"


routesGenerator := InjectedRoutesGenerator

routesImport += "utils.route.Binders._"
// silhouette ----------------

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
