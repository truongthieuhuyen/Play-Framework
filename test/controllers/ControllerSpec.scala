package controllers

import org.scalatestplus.play.PlaySpec
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}

class ControllerSpec extends PlaySpec {
  "HomeController#index" must {
    "give back expected page" in {
      val controller = new HomeController(Helpers.stubControllerComponents())
      val result = controller.index.apply(FakeRequest())
      val bodyText = contentAsString(result)
      bodyText must include("Play-framework-starter-pack")
      bodyText must include("Click to sign in")
      bodyText must include("sign up")
    }
  }
}
