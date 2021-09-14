package models

import scalikejdbc._

case class User(userId: Int,email: String, password: String, name: String, isAdmin: Boolean)
object UsersTable extends SQLSyntaxSupport[User]{
   override val tableName = "user"

  def apply(e: ResultName[User])(rs: WrappedResultSet): User =
    new User(
      rs.int("id"),
      rs.string("email"),
      rs.string("password"),
      rs.string("name"),
      rs.boolean("isAdmin")
    )

  val u = UsersTable.syntax("u")
  def getById(id: Int)(implicit session: DBSession = AutoSession): Option[User] = {
/** val idClause = sqls"where id = ${id}"*/
    sql"select * from ${UsersTable.as(u)} where id = ${id}"
      .map(rs => UsersTable(u.resultName)(rs))
      .single().apply()
  }
}
