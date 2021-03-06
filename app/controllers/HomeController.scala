package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import scala.util.Random

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def person(personName: String, personAge: Int) = Action{
      Ok(s" $personName is  $personAge years old")
  }

  def random() =Action{
    Ok(util.Random.nextInt(100).toString)
  }

  def randomString(length: Int) = Action{
    Ok(util.Random.nextString(length))
  }
}
