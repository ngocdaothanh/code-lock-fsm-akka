This is a "Code Lock" Finite State Machine demo of Erlang:
http://www.erlang.org/doc/design_principles/fsm.html
implemented with Akka 2.3.2.

Checkout:

::

  git clone http://github.com/ngocdaothanh/code-lock-fsm-akka.git

Build and run:

::

  cd code-lock-fsm-akka
  sbt/sbt console

From the SBT console:

::

  import demo.CodeLock
  val actorRef = CodeLock.start(Seq(2, 5, 5, 2))
  CodeLock.button(actorRef, 2)
  CodeLock.button(actorRef, 5)
  CodeLock.button(actorRef, 5)
  CodeLock.button(actorRef, 2)
  CodeLock.stop(actorRef)

To quit the SBT console:

::

  CodeLock.shutdown()
  :q

Or you can also run (see Demo.scala):

::

  sbt/sbt run
