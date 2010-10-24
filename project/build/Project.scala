import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {
  val akkaRepo = "Akka" at "http://www.scalablesolutions.se/akka/repository"

  override def libraryDependencies = Set(
    "se.scalablesolutions.akka" %% "akka-actor" % "1.0-M1"
  ) ++ super.libraryDependencies
}
