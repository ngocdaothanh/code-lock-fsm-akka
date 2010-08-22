package demo

import actor.Fsm
import se.scalablesolutions.akka.actor.Actor

case class Button(digit: Char)

object Lock {
  val TIMEOUT = 5000
}

class Lock(code: String) extends Actor with Fsm[String] {
  def initialState = State(NextState, locked, "")

  def locked: StateFunction = {
    case Event(Button(digit), sofar) =>
      val sofar2 = sofar + digit
      if (sofar2 == code) {
        println("Opened")
        State(NextState, opened, "", Some(Lock.TIMEOUT))
      } else {
        if (sofar2.length < code.length) {
          println("So far: " + sofar2)
          State(NextState, locked, sofar2, Some(Lock.TIMEOUT))
        } else {
          println("Wrong code")
          initialState
        }
      }

    case Event(StateTimeout, _) =>
      println("Reset")
      initialState
  }

  def opened: StateFunction = {
    case Event(_, _) =>
      println("Locked")
      initialState
  }
}
