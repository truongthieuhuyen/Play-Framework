package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import models.UserInMemory
import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def login = Action {
    Ok(views.html.login())
  }

  def register = Action{
    Ok(views.html.register())
  }

  def validateLogin = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("Username").head
      val password = args("Password").head
      if (UserInMemory.validateUser(username, password)) {
        Redirect(routes.TaskList.taskList)
      }
      else {
        Redirect(routes.TaskList.login)
      }
    }.getOrElse(Redirect(routes.TaskList.login))
  }

  def createUser = Action{ request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("Username").head
      val password = args("Password").head
      if (UserInMemory.createUser(username, password)) {
        Redirect(routes.TaskList.taskList)
      }
      else {
        Redirect(routes.TaskList.register)
      }
    }.getOrElse(Redirect(routes.TaskList.register))
  }

  def taskList = Action {
    val username = "mrkable"
    val tasks = UserInMemory.getTasks(username)

    Ok(views.html.taskPage(tasks))
  }
}
