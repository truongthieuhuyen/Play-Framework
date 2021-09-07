package models

import models.Tables._

import scala.concurrent._
import slick.jdbc.MySQLProfile.api._
import org.mindrot.jbcrypt.BCrypt

class TaskDatabaseModel (db: Database)(implicit ec: ExecutionContext){
  def validateUser(username: String, password: String): Future[Boolean] = {
    val matches = db.run(Users.filter(UsersRow => UsersRow.username === username ).result)
    matches.map(UsersRow => UsersRow.filter(UsersRow => BCrypt.checkpw(password, UsersRow.password)).nonEmpty)
  }

  def createUser(username: String, password: String): Future[Boolean] = {
    db.run(Users += UsersRow(-1, username, BCrypt.hashpw(password, BCrypt.gensalt()))).map(addCount => addCount > 0)
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

