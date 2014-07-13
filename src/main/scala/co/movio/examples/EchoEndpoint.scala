package co.movio.examples

import spray.http.StatusCodes
import spray.routing.HttpService

trait EchoEndpoint extends HttpService {
  def route =
    get {
      parameterMap { params ⇒
        complete {
          params.toString
        }
      }
    }
}
