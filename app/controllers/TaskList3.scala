package controllers

import javax.inject.Inject
import play.api.mvc._
import models.{UserData, UserTaskInMemory}
import play.api.libs.json.{JsError, JsSuccess, Json}

class TaskList3 @Inject()(val cc: ControllerComponents) extends AbstractController(cc){
  def load = Action { implicit request =>
    Ok(views.html.mainV3())
  }

  def data = Action{
    Ok(Json.toJson(Seq("a","b","c")))
  }

  implicit val readUserData = Json.reads[UserData]
  def validateUser = Action{implicit request =>
    request.body.asJson.map{body =>
      Json.fromJson[UserData](body) match {
        case JsSuccess(ud, path) =>
          if (UserTaskInMemory.validateUser(ud.username, ud.password)) {
            Ok(Json.toJson(true))
              .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
          }else {
            Ok(views.html.login2())
          }
        case e @ JsError(_) => Redirect(routes.TaskList3.load)
      }
    }.getOrElse(Redirect(routes.TaskList3.load))
  }
}
