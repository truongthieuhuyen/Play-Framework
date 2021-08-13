package controllers

import org.scalatestplus.play._
import models._

class UserTaskInMemorySpec extends PlaySpec {
  "UserTaskInMemory" must {
    "do valid login for default" in (
      UserTaskInMemory.validateUser("mrkable", "pass") mustBe (true)
      )

    "reject login with wrong username" in (
      UserTaskInMemory.validateUser("mrk", "pass") mustBe (false)
      )

    "reject login with wrong password" in (
      UserTaskInMemory.validateUser("mrkable", "pect") mustBe (false)
      )

    "reject login with empty username & password" in (
      UserTaskInMemory.validateUser("", "") mustBe (false)
      )

    "get correct default tasks" in (
      UserTaskInMemory.getTasks("mrkable") mustBe (List("receive", "unbox", "shot"))
      )

    "create new user with no task" in {
      UserTaskInMemory.createUser("tr124", "123456") mustBe (true)
      UserTaskInMemory.getTasks("tr124") mustBe(Nil)
    }

    "create new user with username has existed" in(
      UserTaskInMemory.createUser("mrkable","new1") mustBe(false)
    )

    "add new task for default user " in {
      UserTaskInMemory.addTask("mrkable", "take new task")
      UserTaskInMemory.getTasks("mrkable") must contain("take new task")
    }

    "add new task for a new user" in{
      UserTaskInMemory.addTask("tr124","new task1")
      UserTaskInMemory.getTasks("tr124") must contain("new task1")
    }

    "remove task from default user" in{
      UserTaskInMemory.removeTask("mrkable",UserTaskInMemory.getTasks("mrkable").indexOf("shot"))
      UserTaskInMemory.getTasks("mrkable") must not contain("shot")
    }
  }
}
