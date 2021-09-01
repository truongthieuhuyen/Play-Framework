package models

import scala.collection.mutable


object UserTaskInMemory {
  private val users = mutable.Map[String, String]("admin" -> "password")
  private val tasks = mutable.Map[String, List[String]]("admin" -> List("receive", "unbox", "shot","push"))

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

  def addTask(username: String, task: String): Unit = {
    tasks(username) = task :: tasks.get(username).getOrElse(Nil)

  }

  def removeTask(username: String, index: Int): Boolean = {
    if(index < 0 || index > tasks(username).length || tasks.get(username).isEmpty) false
    else{
      tasks(username) = tasks(username).patch(index,Nil,1)
      true
    }
  }
}
