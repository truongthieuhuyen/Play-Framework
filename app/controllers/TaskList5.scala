package controllers

import javax.inject._
import models._
import play.api.mvc._
import play.api.libs.json._
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext


@Singleton
class TaskList5 @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  private val model = new TaskDatabaseModel(db)

  def load = Action { implicit request =>
    Ok(views.html.mainV5())
  }

  implicit val userDataReads = Json.reads[UserData]
  implicit val taskItemWrites = Json.writes[TaskItem]

  def withJsonBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]): Future[Result] = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Future.successful(Redirect(routes.TaskList3.load))
      }
    }.getOrElse(Future.successful(Redirect(routes.TaskList3.load)))
  }

  def withSessionUsername(f: String => Result)(implicit request: Request[AnyContent]) = {
    request.session.get("username").map(f).getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def withSessionUserid(f: Int => Result)(implicit request: Request[AnyContent]) = {
    request.session.get("userid").map(userid => f(userid.toInt)).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  def validate = Action.async { implicit request =>
    withJsonBody[UserData] { ud =>
      model.validateUser(ud.username,ud.password).map { userExists =>
        if (userExists) {
          Ok(Json.toJson(true))
            .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
        } else {
          Ok(Json.toJson(false))
        }
      }
    }
  }

  def createUser = Action.async { implicit request =>
    withJsonBody[UserData] { ud =>
      model.createUser(ud.username,ud.password).map { userCreated =>
        if (userCreated) {
          Ok(Json.toJson(true))
            .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
        } else {
          Ok(Json.toJson(false))
        }
      }
    }
  }

  def taskList = TODO
//    Action { implicit request =>
//    withSessionUsername { username =>
//      println("!!! Getting tasks")
//      Ok(Json.toJson(UserTaskInMemory.getTasks(username)))
//    }
//  }

  def addTask = TODO
//    Action { implicit request =>
//    withSessionUsername { username =>
//      withJsonBody[String] { task =>
//        UserTaskInMemory.addTask(username,task);
//        Ok(Json.toJson(true))
//      }
//    }
//  }

  def deleteTask = TODO
//    Action { implicit request =>
//    withSessionUsername(username =>
//      withJsonBody[Int] { index =>
//        UserTaskInMemory.removeTask(username,index)
//        Ok(Json.toJson(true))
//      }
//    )
//  }

  def logout = Action { implicit request =>
    Ok(Json.toJson(true)).withSession(request.session - "username")
  }

}
