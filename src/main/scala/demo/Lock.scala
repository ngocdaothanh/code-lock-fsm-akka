package demo

import akka.actor.{Actor, FSM}

object Lock {
  val TIMEOUT = 5000
}

// States
sealed trait LockState
case object Locked extends LockState
case object Opened extends LockState

// Message
case class Button(digit: Char)

class Lock(code: String) extends Actor with FSM[LockState, String] {
  notifying {
    case Transition(Locked, Opened) =>
      log.info("The lock is opened")
    case Transition(Opened, Locked) =>
      log.info("The lock is locked")
  }

  when(Locked) {
    case Event(Button(digit), sofar) =>
      val sofar2 = sofar + digit
      if (sofar2 == code) {
        goto(Opened) using("") until(Lock.TIMEOUT)
      } else {
        if (sofar2.length < code.length) {
          log.info("So far: " + sofar2)
          stay using(sofar2) until(Lock.TIMEOUT)
        } else {
          log.info("Wrong code")
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

  // This should be the last line,
  // otherwise there will be NullPointerException when this actor starts!
  startWith(Locked, "")
}
