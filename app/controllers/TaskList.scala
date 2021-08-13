package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import models.UserTaskInMemory
import play.api.mvc.{BaseController, ControllerComponents, MessagesAbstractController, MessagesControllerComponents}
import play.api.data._
import play.api.data.Forms._

import javax.inject.{Inject, Singleton}

case class LoginData(username: String, password: String)

@Singleton
class TaskList @Inject()(val cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  val loginForm = Form(mapping(
    "Username" -> text(3,10),
    "Password" -> text(4)
  )(LoginData.apply)(LoginData.unapply))

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
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
      if (UserTaskInMemory.validateUser(username, password)) {
        Redirect(routes.TaskList.taskList).withSession("username" -> username)
      }
      else {
        Redirect(routes.TaskList.login).flashing("error" -> "incorrect username/password ")
      }
    }.getOrElse(Redirect(routes.TaskList.login))
  }

  def validateLoginForm = Action { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      ld =>
        if (UserTaskInMemory.validateUser(ld.username, ld.password)) {
          Redirect(routes.TaskList.taskList).withSession("username" -> ld.username)
        }
        else {
          Redirect(routes.TaskList.login).flashing("error" -> "incorrect username/password ")
        }
    )
  }

  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("Username").head
      val password = args("Password").head
      if (UserTaskInMemory.createUser(username, password)) {
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
      val tasks = UserTaskInMemory.getTasks(username)
      Ok(views.html.taskPage(tasks))
    }.getOrElse(Redirect(routes.TaskList.login))
  }

  def addTask = Action { implicit request =>
    val usernameOptions = request.session.get("username")
    usernameOptions.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val task = args("newTask").head
        UserTaskInMemory.addTask(username, task)
        Redirect(routes.TaskList.taskList)
      }.getOrElse(Redirect(routes.TaskList.taskList))
    }.getOrElse(Redirect(routes.TaskList.login))

  }

  def removeTask = Action { implicit request =>
    val usernameOptions = request.session.get("username")
    usernameOptions.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val index = args("index").head.toInt
        UserTaskInMemory.removeTask(username,index)
        Redirect(routes.TaskList.taskList)
      }.getOrElse(Redirect(routes.TaskList.taskList))
    }.getOrElse(Redirect(routes.TaskList.login))
  }

  def badRequest = Action{ implicit request =>
    Ok(views.html.badRequest())
  }
}
