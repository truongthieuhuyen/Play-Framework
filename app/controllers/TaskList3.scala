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
    request.session.get("name").map(function).getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def validateUser = Action { implicit request =>
    withJsonBody[UserData] { ud =>
      if (UserTaskInMemory.validateUser(ud.name, ud.password)) {
        Ok(Json.toJson(true))
          .withSession("name" -> ud.name, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
      } else {
        Ok(Json.toJson(false))
      }
    }
  }

  def createUser = Action { implicit request =>
    withJsonBody[UserData] { ud =>
      if (UserTaskInMemory.createUser(ud.name, ud.password)) {
        Ok(Json.toJson(true))
          .withSession("name" -> ud.name, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
      } else {
        Ok(Json.toJson(false))
      }
    }
  }

  def taskList = Action { implicit request =>
    withSessionUsername { name =>
      Ok(Json.toJson(UserTaskInMemory.getTasks(name)))
    }
  }

  def addTask = Action{ implicit request =>
    withSessionUsername(name =>
      withJsonBody[String] { task =>
        UserTaskInMemory.addTask(name,task);
        Ok(Json.toJson(true))
      }
    )
  }

  def deleteTask = Action{implicit request =>
    withSessionUsername(name =>
      withJsonBody[Int] { index =>
        UserTaskInMemory.removeTask(name,index)
        Ok(Json.toJson(true))
      }
    )
  }

  def logout = Action {implicit request =>
    Ok(Json.toJson(true)).withSession(request.session - "name")
  }
}
