package co.movio.examples

import org.scalatest._
import spray.http.StatusCodes
import spray.testkit.ScalatestRouteTest

class EchoEndpointTest
  extends FunSpec
  with ShouldMatchers
  with ScalatestRouteTest
  with EchoEndpoint {

  def actorRefFactory = system

  it("returns 200 OK for any request") {
    Get() ~> route ~> check {
      status shouldBe StatusCodes.OK
    }
  }

  it("returns its request params as a map in the body") {
    Get("?a=1&b=2") ~> route ~> check {
      responseAs[String] shouldBe "Map(a -> 1, b -> 2)"
    }
  }
}
