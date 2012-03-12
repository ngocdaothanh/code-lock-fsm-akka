package demo

import akka.actor.{Actor, FSM}

// States
sealed trait LockState
case object Locked extends LockState
case object Opened extends LockState

// Events (other than FSM.StateTimeout)
case class Button(digit: Char)

object CodeLock {
  import akka.util.duration._
  val TIMEOUT = 3 seconds
}

class CodeLock(code: String) extends Actor with FSM[LockState, String] {
  import CodeLock._
  import FSM._

  startWith(Locked, "")
  log.info("Code: " + code)

  when(Locked) {
    case Event(Button(digit), sofar) =>
      val sofar2 = sofar + digit
      log.info("So far: " + sofar2)

      if (sofar2 == code) {
        goto(Opened) using("") forMax(TIMEOUT)
      } else {
        if (sofar2.length < code.length) {
          stay using(sofar2) forMax(TIMEOUT)
        } else {
          log.info("Wrong code: " + sofar2)
          stay using("")
        }
      }

    case Event(StateTimeout, _) =>
      log.info("Reset")
      stay using("")
  }

  when(Opened) {
    case Event(_, _) =>
      goto(Locked) using("")
  }

  onTransition {
    case Locked -> Opened =>
      log.info("The lock is opened")
    case Opened -> Locked =>
      log.info("The lock is locked")
  }

  initialize
}
