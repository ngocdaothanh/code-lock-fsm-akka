package demo

import akka.actor.{ActorSystem, Props}

object Demo {
  def main(args: Array[String]) {
    val system = ActorSystem("LockSystem")

    val lock = system.actorOf(Props(new Lock("2552")))
    lock ! Button('2')
    Thread.sleep(1000)
    lock ! Button('5')
    Thread.sleep(1000)
    lock ! Button('5')
    Thread.sleep(1000)
    lock ! Button('2')
    Thread.sleep(6000)

    system.shutdown()
  }
}
