package controllers

import javax.inject._
import models.{User, User}
import play.api.mvc.{BaseController, ControllerComponents}
import scalikejdbc._

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
  def getAllUser() = TODO

  def getUserById() = TODO

  def createUser() = TODO

  def updateAccount() = TODO

  def deleteUser() = TODO


}
