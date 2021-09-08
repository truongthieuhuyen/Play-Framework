package models

import models.Tables._

import scala.concurrent._
import slick.jdbc.MySQLProfile.api._
import org.mindrot.jbcrypt.BCrypt

class TaskDatabaseModel (db: Database)(implicit ec: ExecutionContext){
  def validateUser(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Users.filter(userRow => userRow.username === username ).result)
    matches.map(userRows => userRows.headOption.map {
      userRow => if (BCrypt.checkpw(password, userRow.password)) Some(userRow.userId) else None
    })
  }

  def createUser(username: String, password: String): Future[Boolean] = {
    val matches = db.run(Users.filter(UsersRow => UsersRow.username === username ).result)
    matches.flatMap{userRows =>
      if (userRows.nonEmpty){
        db.run(Users += UsersRow(-1, username, BCrypt.hashpw(password, BCrypt.gensalt())))
          .map(addCount => addCount > 0)
      } else Future.successful(false)
    }
  }

  def getTasks(username: String): Future[Seq[String]] = {
    db.run(
      (for {
        user <- Users if user.username === username;
        item <- Items if item.userId === user.userId
      } yield {
        item.text
      }).result
    )
  }

  def addTask(userid: Int, task: String): Future[Int] = {
    db.run(Items += ItemsRow(-1, userid,task))
  }

  def removeTask(itemId: Int): Future[Boolean] = {
    db.run(Items.filter(_.itemId === itemId).delete).map(count => count > 0)
  }
}

