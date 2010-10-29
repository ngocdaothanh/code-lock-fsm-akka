import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {
  val akkaRepo = "Akka" at "http://www.scalablesolutions.se/akka/repository"

  override def libraryDependencies = Set(
    "akka" %% "akka-actor"  % "1.0-SNAPSHOT",
		"akka" %% "akka-remote" % "1.0-SNAPSHOT"
  ) ++ super.libraryDependencies

  override def unmanagedClasspath = super.unmanagedClasspath +++ ("config")
}
