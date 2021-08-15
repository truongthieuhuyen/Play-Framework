package controllers

import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class TaskListSpec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
  "Task list" must {
    "login and access functions " in {
      go to s"http://localhost:$port/login"
      pageTitle mustBe "Login"
      find(cssSelector("h2")).isEmpty mustBe false
      find(cssSelector("h2")).foreach(e => e.text mustBe ("Login"))

      click on "username-login"
      textField(id("username-login")).value = "mrkable"
      click on "password-login"
      pwdField(id("password-login")).value = "pass"
      submit()
      eventually {
        println("In eventually")
        println(currentUrl)
        pageTitle mustBe("Task List")
        println("Title passed")
        find(cssSelector("h1")).isEmpty mustBe(false)
        println("found h2")
        find(cssSelector("h1")).foreach(e => e.text mustBe "Task")
      }
    }
  }
}
