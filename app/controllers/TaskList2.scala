package controllers

import javax.inject.Inject
import play.api.mvc._
import models.UserTaskInMemory

class TaskList2 @Inject()(val cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    Ok(views.html.mainV2())
  }

  def login2 = Action {
    Ok(views.html.login2())
  }

  def validateUser(username: String, password: String) = Action {
    if (UserTaskInMemory.validateUser(username, password)) {
      Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username))).withSession("username" -> username)
    }
    else {
      Ok(views.html.login2())
    }
  }

  def createUser(username: String, password: String) = Action{
    if (UserTaskInMemory.createUser(username, password)) {
      Ok(views.html.taskPage2(UserTaskInMemory.getTasks(username))).withSession("username" -> username)
    }
    else {
      Ok(views.html.register2())
    }
  }

  def deleteTask(index: Int) =Action{ implicit request =>
    Ok("deleting")
  }
}
