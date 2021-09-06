package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.i18n._
import models._
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}

class TaskList3 @Inject()( cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    Ok(views.html.mainV3())
  }

  implicit val readUserData = Json.reads[UserData]

  def data = Action {
    Ok(Json.toJson(Seq("a", "b", "c")))
  }

  def withJsonBody[A](function: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]) = {
    request.body.asJson.map(body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => function(a)
        case e@JsError(_) => Redirect(routes.TaskList3.load)
      }
    ).getOrElse(Redirect(routes.TaskList3.load))
  }

  def withSessionUsername(function: String => Result)(implicit request: Request[AnyContent]) = {
    request.session.get("username").map(function).getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def validateUser = Action { implicit request =>
    withJsonBody[UserData] { ud =>
      if (UserTaskInMemory.validateUser(ud.username, ud.password)) {
        Ok(Json.toJson(true))
          .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
      } else {
        Ok(Json.toJson(false))
      }
    }
  }

  def createUser = Action { implicit request =>
    withJsonBody[UserData] { ud =>
      if (UserTaskInMemory.createUser(ud.username, ud.password)) {
        Ok(Json.toJson(true))
          .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
      } else {
        Ok(Json.toJson(false))
      }
    }
  }

  def taskList = Action { implicit request =>
    withSessionUsername { username =>
      Ok(Json.toJson(UserTaskInMemory.getTasks(username)))
    }
  }

  def addTask = Action{ implicit request =>
    withSessionUsername(username =>
      withJsonBody[String] { task =>
        UserTaskInMemory.addTask(username,task);
        Ok(Json.toJson(true))
      }
    )
  }

  def delete = Action{implicit request =>
    withSessionUsername(username =>
      withJsonBody[Int] { index =>
        UserTaskInMemory.removeTask(username,index)
        Ok(Json.toJson(true))
      }
    )
  }

  def logout = Action {implicit request =>
    Ok(Json.toJson(true)).withSession(request.session - "username")
  }
}
