package controllers

import javax.inject._
import play.api.mvc.{BaseController, ControllerComponents}
import scalikejdbc._

class ItemController @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
  def getItem() = TODO

  def getAllItem() = TODO

  def addNewItem() = TODO

  def editItem() = TODO

  def removeItem() = TODO
}
