package co.movio

import akka.actor.ActorRef
import spray.json.DefaultJsonProtocol

package object examples {
  type TheatreId = Int
  type ResourceName = String

  object Theatre extends DefaultJsonProtocol {
    implicit val jf = jsonFormat2(Theatre.apply)
  }
  case class Theatre(
    id: TheatreId,
    name: String
  )

  case class Register(
    name: ResourceName,
    actorRef: ActorRef
  )
}
