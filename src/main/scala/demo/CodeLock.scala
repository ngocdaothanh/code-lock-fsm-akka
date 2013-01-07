package demo

import akka.actor.{Actor, ActorRef, ActorSystem, FSM, Props}

// States
sealed trait LockState
case object Locked extends LockState
case object Opened extends LockState

// Events (other than FSM.StateTimeout)
case class Button(digit: Int)

object CodeLock {
  import scala.concurrent.duration._
  val TIMEOUT = 3.seconds

  val system = ActorSystem("LockSystem")

  def start(code: Seq[Int]) =
    system.actorOf(Props(new CodeLock(code)))

  def stop(ref: ActorRef) {
    system.stop(ref)
  }

  def shutdown() {
    system.shutdown()
  }

  def button(ref: ActorRef, digit: Int) {
    ref ! Button(digit)
  }
}

class CodeLock(code: Seq[Int]) extends Actor with FSM[LockState, Seq[Int]] {
  import CodeLock._
  import FSM._

  startWith(Locked, Nil)
  log.info("Code: " + code)

  when(Locked) {
    case Event(Button(digit), sofar) =>
      val sofar2 = sofar :+ digit
      log.info("So far: " + sofar2)

      if (sofar2 == code) {
        goto(Opened) using(Nil) forMax(TIMEOUT)
      } else {
        if (sofar2.length < code.length) {
          stay using(sofar2) forMax(TIMEOUT)
        } else {
          log.info("Wrong code: " + sofar2)
          stay using(Nil)
        }
      }

    case Event(StateTimeout, _) =>
      log.info("Reset")
      stay using(Nil)
  }

  when(Opened) {
    case Event(_, _) =>
      goto(Locked) using(Nil)
  }

  onTransition {
    case Locked -> Opened =>
      log.info("The lock is opened")
    case Opened -> Locked =>
      log.info("The lock is locked")
  }

  override def preStart() = {
    log.info("This lock has been started: " + self)
  }

  override def postStop() = {
    log.info("This lock has been stopped: " + self)
  }

  initialize
}
