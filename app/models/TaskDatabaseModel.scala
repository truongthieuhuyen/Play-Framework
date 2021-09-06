package models

import scala.concurrent.{ExecutionContext, Future}

class TaskDatabaseModel (db: DataBase)(implicit ec: ExecutionContext){
  def validateUser(username: String, password: String):Future[Option[Int]] = {
    val matches = db.run()
  }
}

case class DataBase()
