package models

import akka.http.scaladsl.model.DateTime
import play.api.libs.json._


case class UserData(email: String, password: String, name: String, isAdmin: Boolean) {
  def create: User = User.create(email, password, name, isAdmin)

  def update(userId: Int): User = User(userId, email, password, name, isAdmin)
}

object UserData {
//  private lazy val ISODateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis
//  private lazy val ISODateTimeParser = ISODateTimeFormat.dateTimeParser
//
//  implicit val DateTimeFormatter = new Format[DateTime]{
//    def reads(j: JsValue) = JsSuccess(ISODateTimeParser.parse(j.as[String]))
//    def writes(o: DateTime): JsValue = JsString(ISODateTimeFormatter.print(o))
//  }

  implicit val userDataReads = Json.reads[UserData]
  implicit val userDataWrites = Json.writes[UserData]

  def fromUser(ut: User): UserData = UserData(ut.email,ut.password,ut.name,ut.isAdmin)
}