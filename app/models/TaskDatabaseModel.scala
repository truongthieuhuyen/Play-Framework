//package models
//
//import models.Tables._
//
//import scala.concurrent._
//import slick.jdbc.MySQLProfile.api._
//import org.mindrot.jbcrypt.BCrypt
//import scalikejdbc._
//
//class TaskDatabaseModel (db: Database)(implicit ec: ExecutionContext){
//  def validateUser(username: String, password: String): Future[Option[Int]] = {
//    val matches = db.run(Users.filter(userRow => userRow.username === username ).result)
//    matches.map(userRows => userRows.headOption.map {
//      userRow => if (BCrypt.checkpw(password, userRow.password)) userRow.userId else 0
//    })
//  }
//
//  def createUser(username: String, password: String): Future[Boolean] = {
//    val matches = db.run(Users.filter(UsersRow => UsersRow.username === username ).result)
//    matches.flatMap{userRows =>
//      if (userRows.nonEmpty){
////        val stt = db.run(Users.filter(UsersRow => UsersRow.userId === userRows.indexOf() + 1).result)
//        db.run(Users += UsersRow(1, username, BCrypt.hashpw(password, BCrypt.gensalt())))
//          .map(addCount => addCount > 0)
//      } else Future.successful(false)
//    }
//  }
//
//  def getTasks(username: String): Future[Seq[String]] = {
//    db.run(
//      (for {
//        user <- Users if user.username === username
//        item <- Items if item.userId === user.userId
//      } yield {
//        item.text
//      }).result
//    )
//  }
//
//  def addTask(userid: Int, task: String): Future[Int] = {
//    db.run(Items += ItemsRow(-1, userid,task))
//  }
//
//  def removeTask(itemId: Int): Future[Boolean] = {
//    db.run(Items.filter(_.itemId === itemId).delete).map(count => count > 0)
//  }
//
//  /** scalike JDBC*/
//  def getNameById(id: Int)(implicit session: DBSession = AutoSession): Option[String] = SQL("select * from users where id = {id}")
//    .bindByName(Symbol("id") -> id)
//    .map { rs => rs.string("name") }
//    .single().apply()
//
//  def getEmailOfFirstEmployee()(implicit session: DBSession = AutoSession): Option[String] =
//    SQL("select * from users")
//      .map { rs => rs.string("email") }
//      .first().apply()
//
//  def getNameList()(implicit session: DBSession = AutoSession): List[String] =
//    SQL("select * from users")
//      .map { rs => rs.string("name") }
//      .list().apply()
//
//  def getNameListByForeach()(implicit session: DBSession = AutoSession): Unit =
//    SQL("select * from users")
//      .foreach(rs => print(rs.string("name")))
//
//  def updateNameById(id: Int, newName: String)(implicit session: DBSession = AutoSession): Int =
//    SQL("update users set name = {name} where id = {id}")
//      .bindByName(Symbol("id") -> id, Symbol("name") -> newName)
//      .update().apply()
//
//  def deleteById(id: Int)(implicit session: DBSession = AutoSession): Boolean =
//    SQL("delete from users where id = {id}")
//      .bindByName(Symbol("id") -> id)
//      .execute().apply()
//
//
//}
//
