package demo

import akka.actor.{Actor, FSM}
import akka.event.EventHandler
import akka.util.duration._

object Lock {
  val TIMEOUT = 5 seconds
}

// States
sealed trait LockState
case object Locked extends LockState
case object Opened extends LockState

// Message
case class Button(digit: Char)

class Lock(code: String) extends Actor with FSM[LockState, String] {
  import Lock._
  import FSM._

  startWith(Locked, "")

  when(Locked) {
    case Event(Button(digit), sofar) =>
      val sofar2 = sofar + digit
      if (sofar2 == code) {
        goto(Opened) using("") forMax(TIMEOUT)
      } else {
        if (sofar2.length < code.length) {
          EventHandler.info(this, "So far: " + sofar2)
          stay using(sofar2) forMax(TIMEOUT)
        } else {
          EventHandler.info(this, "Wrong code: " + sofar2)
          stay using("")
        }
      }

    case Event(StateTimeout, _) =>
      EventHandler.info(this, "Reset")
      stay using("")
  }

  when(Opened) {
    case Event(_, _) =>
      goto(Locked) using("")
  }

  onTransition {
    case Locked -> Opened =>
      EventHandler.info(this, "The lock is opened")
    case Opened -> Locked =>
      EventHandler.info(this, "The lock is locked")
  }

  initialize
}
