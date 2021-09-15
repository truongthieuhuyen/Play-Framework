package controllers

import javax.inject._
import models._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json._
import play.api.mvc._
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent._

@Singleton
class TaskList5 @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  private val model = new TaskDatabaseModel(db)

  def load = Action { implicit request =>
    Ok(views.html.mainV5())
  }

  implicit val userDataReads = Json.reads[UserData]
  implicit val userItemWrites = Json.writes[UserData]

  def withJsonBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]): Future[Result] = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Future.successful(Redirect(routes.TaskList3.load))
      }
    }.getOrElse(Future.successful(Redirect(routes.TaskList3.load)))
  }

  def withSessionUsername(f: String => Result)(implicit request: Request[AnyContent]) = {
    request.session.get("name").map(f).getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def withSessionUserid(f: Int => Result)(implicit request: Request[AnyContent]) = {
    request.session.get("userId").map(userId => f(userId.toInt)).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  def validate = Action.async { implicit request =>
    withJsonBody[UserData] { ud =>
      model.validateUser(ud.name,ud.password).map { userExists =>
        userExists match {
          case Some(userId) => Ok(Json.toJson(true))
            .withSession("name" -> ud.name, "userId" -> userId.toString, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
          case None =>
            Ok(Json.toJson(false))
        }
      }
    }
  }

  def createUser = Action.async { implicit request =>
    withJsonBody[UserData] { ud =>
      model.createUser(ud.name,ud.password).map { userCreated =>
        if (userCreated) {
          Ok(Json.toJson(true))
            .withSession("name" -> ud.name, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
        } else {
          Ok(Json.toJson(false))
        }
      }
    }
  }

  def taskList = TODO
//    Action.async { implicit request =>
//    withSessionUsername { name =>
//      println("!!! Getting tasks")
//      model.getTasks(name).map(tasks => Ok(Json.toJson(tasks)))
//    }
//  }

  def addTask = TODO
//  Action.async { implicit request =>
//    withSessionUserid{ userId =>
//      withJsonBody[String] { task =>
//        model.addTask(userId,task).map(count => Ok(Json.toJson(count > 1)))
//      }
//    }
//  }

  def deleteTask = TODO
//    Action.async { implicit request =>
//    withSessionUsername(name =>
//      withJsonBody[Int] { itemId =>
//        model.removeTask(itemId).map(removed => Ok(Json.toJson(removed)))
//        Ok(Json.toJson(true))
//      }
//    )
//  }

  def logout = Action { implicit request =>
    // need to change the route
    Ok(Json.toJson(true)).withSession(request.session - "name")
  }


}
