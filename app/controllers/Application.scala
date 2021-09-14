package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}

@Singleton
class Application @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
  def api = Action {implicit request =>
    val json = Json.obj(
      "version" -> "0.1",
      "links" -> Seq(
        Json.obj("rel" -> "user","href" -> routes.UserController.getAllUser.absoluteURL())
      )
    )
    Ok(json)
  }
}
