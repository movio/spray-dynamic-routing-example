package co.movio.examples

import akka.actor._
import spray.http.StatusCodes
import spray.routing.HttpServiceActor
import spray.routing.Route

class MasterHandler extends HttpServiceActor {
  var endpoints = Map.empty[ResourceName, ActorRef]

  def createEndpointRoute(name: ResourceName, target: ActorRef): Route =
    pathPrefix(name) {
      ctx => target ! ctx
    }

  def completeRoute: Route =
    endpoints.foldLeft[Route](reject) { (acc, next) =>
      val (name, target) = next
      val endpointRoute = createEndpointRoute(name, target)
      endpointRoute ~ acc
    }

  val registerReceive: Receive = {
    case Register(name, actor) =>
      endpoints = endpoints.updated(name, actor)
      context become receive
  }

  def receive = registerReceive orElse runRoute(completeRoute)
}
