package demo

object Demo {
  def main(args: Array[String]) {
    val actorRef = CodeLock.start(Seq(2, 5, 5, 2))

    CodeLock.button(actorRef, 2)
    Thread.sleep(1000)
    CodeLock.button(actorRef, 5)
    Thread.sleep(1000)
    CodeLock.button(actorRef, 5)
    Thread.sleep(1000)
    CodeLock.button(actorRef, 2)
    Thread.sleep(3500)

    CodeLock.stop(actorRef)
    CodeLock.shutdown()
  }
}
