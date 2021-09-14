package controllers

import javax.inject._
import models._
import play.api.mvc._
import play.api.libs.json.Json
import scalikejdbc._


class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def getAllUser = Action{ implicit request =>
    val user = User.findAll()
    val json = Json.obj("user" -> user.map { ut =>
      val userData = UserData.fromUser(ut)
      Json.toJson(userData)
    })
    Ok(json)
  }

  def getById() = TODO

  def createUser() = TODO

  def updateAccount() = TODO

  def deleteUser() = TODO


}
