package controllers

import models._
import org.scalatestplus.play._

class UserTaskInMemorySpec extends PlaySpec {
  "UserTaskInMemory" must {
    "do valid login for default" in (
      UserTaskInMemory.validateUser("admin", "password") mustBe (true)
      )

    "reject login with wrong username" in (
      UserTaskInMemory.validateUser("admin123", "password") mustBe (false)
      )

    "reject login with wrong password" in (
      UserTaskInMemory.validateUser("admin", "pect") mustBe (false)
      )

    "reject login with empty username & password" in (
      UserTaskInMemory.validateUser("", "") mustBe (false)
      )

    "get correct default tasks" in (
      UserTaskInMemory.getTasks("admin") mustBe (List("receive", "unbox", "shot","push"))
      )

    "create new user with no task" in {
      UserTaskInMemory.createUser("tr124", "123456") mustBe (true)
      UserTaskInMemory.getTasks("tr124") mustBe(Nil)
    }

    "create new user with username has existed" in(
      UserTaskInMemory.createUser("admin","new1") mustBe(false)
    )

    "add new task for default user " in {
      UserTaskInMemory.addTask("admin", "take new task")
      UserTaskInMemory.getTasks("admin") must contain("take new task")
    }

    "add new task for a new user" in{
      UserTaskInMemory.addTask("tr124","new task1")
      UserTaskInMemory.getTasks("tr124") must contain("new task1")
    }

    "remove task from default user" in{
      UserTaskInMemory.removeTask("admin",UserTaskInMemory.getTasks("admin").indexOf("shot"))
      UserTaskInMemory.getTasks("admin") must not contain("shot")
    }
  }
}
