package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def taskList = Action {
    val tasks = List("wake up", "breakfast", "study", "eat lunch", "study", "resting-time", "sleep")

    Ok(views.html.taskPage(tasks))
  }

  def login = Action {
    Ok(views.html.login())
  }

  def validateLogin = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map{args =>
      val username = args("Username").head
      val password = args("Password").head

      Redirect(routes.TaskList.taskList)
    }.getOrElse(Redirect(routes.TaskList.login))
  }

}
