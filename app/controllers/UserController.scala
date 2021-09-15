package controllers

import javax.inject._
import models._
import play.api.libs.json._
import play.api.mvc._

import scala.collection.mutable


class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
  private val users = mutable.Map[String, String]("admin" -> "password")

  def withJsonBody[A](function: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]) = {
    request.body.asJson.map(body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => function(a)
        case e@JsError(_) => Redirect(routes.TaskList3.load)
      }
    ).getOrElse(Redirect(routes.TaskList3.load))
  }

  def getAllUser = Action { implicit request =>
    val user = User.findAll()
    val json = Json.obj("user" -> user.map { ut =>
      val userData = UserData.fromUser(ut)
      Json.toJson(userData)
    })
    Ok(json)
  }

  def getById(userId: Int) = Action {implicit request =>
    User.find(userId) match {
      case Some(ut) =>
        val userData = UserData.fromUser(ut)
        val json = Json.toJson(userData)

        Ok(json)
      case None => NotFound(Json.obj("error" -> "Not found!"))
    }
  }

  def createUser(email: String, password: String, name: String) = Action(parse.json) { implicit request =>
    request.body.validate[UserData].fold(
      error => BadRequest(Json.obj("error" -> "Json was not correct")),
      userData => {
        val user = userData.create
        Created.withHeaders(LOCATION -> routes.UserController.getById(user.userId).absoluteURL())
      }
    )
  }

  def updateAccount() = TODO

  def deleteUser() = TODO


}
