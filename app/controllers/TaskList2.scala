package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.i18n._
import models.UserTaskInMemory

class TaskList2 @Inject()(val cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    val usernameOptions = request.session.get("username")
    usernameOptions.map { username =>
      Ok(views.html.mainV2(routes.TaskList2.getTask.toString))
    }.getOrElse(Ok(views.html.mainV2(routes.TaskList2.login.toString)))
  }

  def login = Action { implicit request =>
    Ok(views.html.login2())
  }

  def logout = Action {
    Redirect(routes.TaskList2.load).withNewSession
  }

  def getTask = Action{ implicit request =>
    val usernameOptions = request.session.get("username")
    usernameOptions.map { username =>
      Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username)))
    }.getOrElse(Ok(views.html.login2()))
  }


//  def register = Action {
//    Ok(views.html.register2())
//  }

  def validateUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (UserTaskInMemory.validateUser(username, password)) {
        Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username)))
          .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
      }
      else {
        Ok(views.html.login2())
      }
    }.getOrElse(Ok(views.html.login2()))

  }

  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("Username").head
      val password = args("Password").head
      if (UserTaskInMemory.createUser(username, password)) {
        Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username)))
          .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
      }
      else {
        Ok(views.html.login2())
      }
    }.getOrElse(Ok(views.html.login2()))

  }

  def deleteTask = Action { implicit request =>
    val usernameOptions = request.session.get("username")
    usernameOptions.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val index = args("index").head.toInt
        UserTaskInMemory.removeTask(username, index)
        Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username)))
      }.getOrElse(Ok(views.html.login2()))
    }.getOrElse(Ok(views.html.login2()))
  }

  def addTask = Action { implicit request =>
    val usernameOptions = request.session.get("username")
    usernameOptions.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val task = args("task").head
        UserTaskInMemory.addTask(username, task)
        Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username)))
      }.getOrElse(Ok(views.html.login2()))
    }.getOrElse(Ok(views.html.login2()))
  }

  def generatedJS = Action {
    Ok(views.js.generatedJS())
  }
}
