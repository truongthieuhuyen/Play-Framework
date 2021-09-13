package models

import scalikejdbc._

case class User(id: Long, name: String, email: String)
object UsersTable extends SQLSyntaxSupport[User]{
   override val tableName = "user"

  def apply(e: ResultName[User])(rs: WrappedResultSet): User =
    new User(
      rs.int("id"),
      rs.string("name"),
      rs.string("email")
    )

  val u = UsersTable.syntax("u")
  def getById(id: Int)(implicit session: DBSession = AutoSession): Option[User] = {
/** val idClause = sqls"where id = ${id}"*/
    sql"select * from ${UsersTable.as(u)} where id = ${id}"
      .map(rs => UsersTable(u.resultName)(rs))
      .single().apply()
  }
}
