package controllers

import javax.inject.Inject
import play.api.mvc._
import models.UserTaskInMemory

class TaskList2 @Inject()(val cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    Ok(views.html.mainV2())
  }

  def login = Action {
    Ok(views.html.login2())
  }
  def logout = Action{
    Redirect(routes.TaskList2.load).withNewSession
  }
  def register =Action{
    Ok(views.html.register2())
  }

  def validateUser = Action {implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (UserTaskInMemory.validateUser(username, password)) {
        Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username))).withSession("username" -> username)
      }
      else {
        Ok(views.html.login2())
      }
    }.getOrElse(Ok(views.html.login2()))


  }

  def createUser(username: String, password: String) = Action {
    if (UserTaskInMemory.createUser(username, password)) {
      Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username))).withSession("username" -> username)
    }
    else {
      Ok(views.html.register2())
    }
  }

  def deleteTask(index: Int) = Action { implicit request =>
    val usernameOptions = request.session.get("username")
    usernameOptions.map { username =>
      UserTaskInMemory.removeTask(username, index)
      Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username)))
    }.getOrElse(Redirect(routes.TaskList2.login))
  }

  def addTask(task: String) = Action{ implicit request =>
    val usernameOptions = request.session.get("username")
    usernameOptions.map { username =>
      UserTaskInMemory.addTask(username, task)
      Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username)))
    }.getOrElse(Redirect(routes.TaskList2.login))
  }
  def generatedJS = Action{
    Ok(views.js.generatedJS())
  }
}
