package controllers

import javax.inject.Inject
import play.api.mvc._
import models.UserTaskInMemory

class TaskListTheSecond @Inject()(val cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    Ok(views.html.mainV2())
  }

  def login = Action {
    Ok(views.html.login2())
  }
}
