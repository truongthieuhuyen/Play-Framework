package controllers

import javax.inject._
import models._
import play.api.libs.json._
import play.api.mvc._
import utils.JsonUtils

import scala.collection.mutable


class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with JsonUtils {
  private val userList = new mutable.ListBuffer[User]()

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

  /** Status 415 UNSUPPORTED MEDIA TYPE */
  def createUser = Action(parse.json) { implicit request =>
    request.body.validate[UserData].fold(
      error => UnsupportedMediaType(Json.obj("error" -> "Json was not correct")),
      userData => {
        val user = userData.create
        Created.withHeaders(LOCATION -> routes.UserController.getById(user.userId).absoluteURL())
      }
    )
  }

  def addNewUser = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson
    val listUser: Option[User] =
      jsonObject.flatMap(
        Json.fromJson[User](_).asOpt
      )

    listUser match {
      case Some(newUser) =>
        val nextId = userList.map(_.userId).max + 1
        val toBeAdded = userList(nextId)
        userList += toBeAdded
        Created(Json.toJson(toBeAdded))
      case None =>
        BadRequest
    }
  }

  /** Status */
  def accountSetting(userId: Int) = Action(parse.json) { implicit request =>
    request.body.validate[UserData].fold(
      error => UnsupportedMediaType(Json.obj("error" -> "Json was not correct")),
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
