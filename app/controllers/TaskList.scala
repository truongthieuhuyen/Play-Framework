package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
def taskList = Action {
  val tasks = List("wake up","breakfast","study","eat lunch","study","resting-time","sleep")

   Ok(views.html.taskPage(tasks))
}
}
