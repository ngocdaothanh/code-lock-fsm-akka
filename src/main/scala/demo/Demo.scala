package demo

import akka.actor.{ActorSystem, Props}

object Demo {
  def main(args: Array[String]) {
    val system = ActorSystem("LockSystem")

    val cl = system.actorOf(Props(new CodeLock("2552")))
    cl ! Button('2')
    Thread.sleep(1000)
    cl ! Button('5')
    Thread.sleep(1000)
    cl ! Button('5')
    Thread.sleep(1000)
    cl ! Button('2')
    Thread.sleep(4000)

    system.shutdown()
  }
}
