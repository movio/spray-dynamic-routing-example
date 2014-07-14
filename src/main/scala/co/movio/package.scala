package co.movio

import spray.json.DefaultJsonProtocol

package object examples {
  type TheatreId = Int

  object Theatre extends DefaultJsonProtocol {
    implicit val jf = jsonFormat2(Theatre.apply)
  }
  case class Theatre(
    id: TheatreId,
    name: String
  )
}
