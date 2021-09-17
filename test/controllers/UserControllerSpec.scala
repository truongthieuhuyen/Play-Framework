package controllers

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Play.materializer
import play.api.test.Helpers._
import play.api.test._

class UserControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting{
  "UserController POST" should{
    "get json data to create new user" in{
      val controllers = new UserController(stubControllerComponents())
      val request = controllers.createUser.apply(FakeRequest(POST,"/"))

      status(request) mustBe OK

//      contentType(request) mustBe Some("text/html")
//      contentAsString(request) must include ("Welcome to Play")
    }
  }
}
