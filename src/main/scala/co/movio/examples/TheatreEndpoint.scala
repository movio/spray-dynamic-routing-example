package co.movio.examples

import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

trait TheatreEndpoint extends HttpService with SprayJsonSupport {
  def dataStore(id: TheatreId): Option[Theatre]

  def route =
    path(IntNumber) { id ⇒
      get {
        complete {
          dataStore(id)
        }
      }
    }
}
