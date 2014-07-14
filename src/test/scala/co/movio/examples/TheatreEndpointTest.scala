package co.movio.examples

import org.scalatest._
import scala.util.control.NoStackTrace
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.testkit.ScalatestRouteTest

class TheatreEndpointTest
  extends FunSpec
  with ShouldMatchers
  with ScalatestRouteTest
  with SprayJsonSupport
  with TheatreEndpoint {

  def actorRefFactory = system

  val testDataStore: Map[TheatreId, Theatre] =
    Map(
      1 → Theatre(1, "one"),
      2 → Theatre(2, "two")
    )

  override def dataStore(id: TheatreId): Option[Theatre] = id match {
    case 0      ⇒ throw new RuntimeException("testing") with NoStackTrace
    case id @ _ ⇒ testDataStore.get(id)
  }

  it("returns a theatre's details with a GET request to /theatres/id") {
    Get("/1") ~> route ~> check {
      responseAs[Theatre] shouldBe Theatre(1, "one")
    }
  }

  it("returns 404 Not Found when the theatre does not exist") {
    Get("/999") ~> route ~> check {
      status shouldBe StatusCodes.NotFound
    }
  }

  it("returns 500 Internal Server Error when an exception is thrown") {
    Get("/0") ~> route ~> check {
      status shouldBe StatusCodes.InternalServerError
    }
  }
}
