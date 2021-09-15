package controllers

import javax.inject._
import models._
import play.api.libs.json._
import play.api.mvc._

import scala.collection.mutable


class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  private val users = mutable.Map[String, String]("admin" -> "password")

//  def withJsonBody[A](function: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]) = {
//    request.body.asJson.map(body =>
//      Json.fromJson[A](body) match {
//        case JsSuccess(a, path) => function(a)
//        case e@JsError(_) => Redirect(routes.TaskList3.load)
//      }
//    ).getOrElse(Redirect(routes.TaskList3.load))
//  }

  /** Status OK */
  def getAllUser = Action { implicit request =>
    val user = User.findAll()
    val json = Json.obj("user" -> user.map { ut =>
      val userData = UserData.fromUser(ut)
      Json.toJson(userData)
    })
    Ok(json)
  }

  /** Status OK */
  def getById(userId: Int) = Action { implicit request =>
    User.find(userId) match {
      case Some(ut) =>
        val userData = UserData.fromUser(ut)
        val json = Json.toJson(userData)
        Ok(json)
      case None => NotFound(Json.obj("error" -> "Not found!"))
    }
  }

  /** Status 400 BAD REQUEST */
  def createUser = Action(parse.json) { implicit request =>
    request.body.validate[UserData].fold(
      error => BadRequest(Json.obj("error" -> "Json was not correct")),
      userData => {
        val user = userData.create
        Created.withHeaders(LOCATION -> routes.UserController.getById(user.userId).absoluteURL())
      }
    )
  }

  /** Status  */
  def accountSetting(userId: Int) = Action(parse.json) { implicit request =>
    request.body.validate[UserData].fold(
      error => BadRequest(Json.obj("error" -> "Json was not correct")),
      userData => {
        User.find(userId) match {
          case Some(ut) => userData.update(ut.userId); NoContent

          case None => NotFound(Json.obj("error" -> "Not found!"))
        }
      }
    )
  }

/** Status OK */
  def deleteUser(userId: Int) = Action { implicit request =>
    User.find(userId) match {
      case Some(ut) =>
        User.destroy(ut)
        Ok(Json.obj("ok" -> "deleted"))
      case None => NotFound(Json.obj("error" -> "Not found!"))
    }

  }


}
