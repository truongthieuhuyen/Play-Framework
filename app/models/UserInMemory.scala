package models

import scala.collection.mutable


object UserInMemory {
  private val users = mutable.Map[String, String]("mrkable" -> "pass")
  private val tasks = mutable.Map[String, List[String]]("mrkable" -> List("receive", "unbox", "shot"))

  def validateUser(username: String, password: String): Boolean = {
    users.get(username).map(_ == password).getOrElse(false)
  }

  def createUser(username: String, password: String): Boolean = {
    if (users.contains(username)) false
    else {
      users(username) = password
      true
    }
  }

  def getTasks(username: String): Seq[String] = {
    tasks.get(username).getOrElse(Nil)
  }

  def addTask(username: String, task: String): Unit = ???

  def removeTask(username: String, index: Int): Boolean = ???
}
