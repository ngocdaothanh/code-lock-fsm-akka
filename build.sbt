organization := "tv.cntt"

name := "code-lock-fsm-akka"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.0"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature"
)

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.2"
