package co.movio.examples

import akka.actor.{ ActorSystem, Props }
import akka.io.IO
import spray.can.Http
import spray.routing.HttpServiceActor

object Main extends App {
  implicit val system = ActorSystem()

  val master = system.actorOf(Props[MasterHandler], name = "master")
  IO(Http) ! Http.Bind(master, interface = "localhost", port = 8080)

  val theatres = system.actorOf(Props[TheatreEndpointActor])
  master ! Register("theatres", theatres)

  val echo = system.actorOf(Props[EchoEndpointActor])
  master ! Register("echo", echo)
}

class TheatreEndpointActor
  extends HttpServiceActor
  with TheatreEndpoint {

  val data = Map(
    1 → Theatre(1, "Theatre #1"),
    2 → Theatre(2, "Theatre #2")
  )

  def dataStore(id: TheatreId) = data.get(id)
  def receive = runRoute(route)
}

class EchoEndpointActor
  extends HttpServiceActor
  with EchoEndpoint {

  def receive = runRoute(route)
}
