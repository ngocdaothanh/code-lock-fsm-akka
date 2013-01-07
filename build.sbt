organization := "tv.cntt"

name := "code-lock-fsm-akka"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.0"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature"
)

resolvers += "Typesafe" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.1.0"
