package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import models.UserInMemory
import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def login = Action { implicit request =>
    Ok(views.html.login ())
  }

  def register = Action { implicit request =>
    Ok(views.html.register())
  }

  def logout = Action {
    Redirect(routes.TaskList.login).withNewSession
  }

  def validateLogin = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("Username").head
      val password = args("Password").head
      if (UserInMemory.validateUser(username, password)) {
        Redirect(routes.TaskList.taskList).withSession("username" -> username)
      }
      else {
        Redirect(routes.TaskList.login)
      }
    }.getOrElse(Redirect(routes.TaskList.login))
  }

  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("Username").head
      val password = args("Password").head
      if (UserInMemory.createUser(username, password)) {
        Redirect(routes.TaskList.taskList).withSession("username" -> username)
      }
      else {
        Redirect(routes.TaskList.register)
      }
    }.getOrElse(Redirect(routes.TaskList.register))
  }

  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = UserInMemory.getTasks(username)
      Ok(views.html.taskPage(tasks))
    }.getOrElse(Redirect(routes.TaskList.login))
  }
}
