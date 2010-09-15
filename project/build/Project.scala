import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {
  //val akkaHome = Path.fromFile(System.getenv("AKKA_HOME"))
  val akkaHome = Path.fromFile(System.getProperty("user.home") + "/plain/akka")

  override def unmanagedClasspath = (akkaHome ** "*.jar") //+++ (akkaHome / "lib_managed" ** "*.jar")
}
