package models

import models.Tables._

import scala.concurrent._
import slick.jdbc.MySQLProfile.api._
import org.mindrot.jbcrypt.BCrypt

class TaskDatabaseModel (db: Database)(implicit ec: ExecutionContext){
  def validateUser(username: String, password: String): Future[Boolean] = {
    val matches = db.run(Users.filter(UsersRow => UsersRow.username === username && UsersRow.password === password).result)
    matches.map(UsersRow => UsersRow.nonEmpty)
  }

  def createUser(username: String, password: String): Boolean = {
    ???
  }

  def getTasks(username: String): Future[Seq[String]] = {
    ???
  }

  def addTask(userid: Int, task: String): Future[Int] = {
    ???
  }

  def removeTask(itemId: Int): Future[Boolean] = {
    ???
  }
}

