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
  when(Locked) {
    case Event(Button(digit), sofar) =>
      val sofar2 = sofar + digit
      if (sofar2 == code) {
        println("Opened")
        goto(Opened) using("") until(Lock.TIMEOUT)
      } else {
        if (sofar2.length < code.length) {
          println("So far: " + sofar2)
          goto(Locked) using(sofar2) until(Lock.TIMEOUT)
        } else {
          println("Wrong code")
          goto(Locked) using("")
        }
      }

    case Event(StateTimeout, _) =>
      println("Reset")
      goto(Locked) using("")
  }

  when(Opened) {
    case Event(_, _) =>
      println("Locked")
      goto(Locked) using("")
  }

  // This should be the last line, or there will be NullPointerException!
  startWith(Locked, "")
}
