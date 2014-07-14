package co.movio.examples

import akka.actor._
import akka.testkit._
import org.scalatest._
import spray.http.HttpResponse
import spray.http.StatusCodes
import spray.httpx.RequestBuilding
import spray.routing.HttpServiceActor
import spray.testkit.ScalatestRouteTest

class DummyEndpoint(response: String = "i'm alive") extends HttpServiceActor {
  def receive = runRoute {
    complete {
      response
    }
  }
}

class MasterHandlerTest
  extends TestKit(ActorSystem())
  with ImplicitSender
  with FunSpecLike
  with ShouldMatchers
  with RequestBuilding
  with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  it("returns 404 Not Found when nothing has been registered") {
    new Fixture {
      master ! Get("/endpoint")
      expectMsgType[HttpResponse].status shouldBe StatusCodes.NotFound
    }
  }

  it("can access an endpoint after registering it") {
    new Fixture {
      val endpointActor = TestActorRef(new DummyEndpoint)
      master ! Register("endpoint", endpointActor)
      master ! Get("/endpoint")
      val response = expectMsgType[HttpResponse]
      response.status shouldBe StatusCodes.OK
      response.entity.data.asString shouldBe "i'm alive"
    }
  }

  it("can register multiple endpoints") {
    new Fixture {
      val endpointActor1 = TestActorRef(new DummyEndpoint("1"))
      master ! Register("endpoint1", endpointActor1)
      val endpointActor2 = TestActorRef(new DummyEndpoint("2"))
      master ! Register("endpoint2", endpointActor2)

      Seq(
        "/endpoint1" → "1",
        "/endpoint2" → "2"
      ) foreach { case (endpoint, expected) ⇒
        master ! Get(endpoint)
        val response = expectMsgType[HttpResponse]
        response.status shouldBe StatusCodes.OK
        response.entity.data.asString shouldBe expected
      }
    }
  }

  trait Fixture {
    val master = TestActorRef(new MasterHandler)
  }
}
