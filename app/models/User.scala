package models

import com.sun.xml.internal.bind.v2.TODO
import scalikejdbc._

case class User(userId: Int,email: String, password: String, name: String, isAdmin: Boolean)
object User extends SQLSyntaxSupport[User]{
  override val tableName = "user"
  override val columns = Seq("user_id","email","password","name","is_admin")

  def apply(e: ResultName[User])(rs: WrappedResultSet): User =
    new User(
      rs.int("userId"),
      rs.string("email"),
      rs.string("password"),
      rs.string("name"),
      rs.boolean("isAdmin")
    )

  val ut = User.syntax("ut")

  def getById(userId: Int)(implicit session: DBSession = AutoSession): Option[User] = {
    /** val idClause = sqls"where id = ${id}"*/
    sql"select * from ${User.as(ut)} where user_id = ${userId}"
      .map(rs => User(ut.resultName)(rs))
      .single().apply()
  }

  def find(userId: Int)(implicit session: DBSession = AutoSession): Option[User] = {
    withSQL{
      select
        .from(User as ut)
        .where.eq(ut.userId, userId)
    }.map(User(ut.resultName)).single().apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = AutoSession): List[User] = {
    withSQL{
      select.from(User as ut).where.append(sqls"${where}")
    }.map(User(ut.resultName)).list().apply()
  }

  def create(email: String, password: String, name: String, isAdmin: Boolean)
            (implicit session: DBSession = AutoSession): User = {
    val generatedKey = withSQL{
      insertInto(User).columns(
        column.email,
        column.password,
        column.name,
        column.isAdmin
      ).values(
        email,
        password,
        name,
        isAdmin
      )
    }.updateAndReturnGeneratedKey().apply()
    User(
      userId = generatedKey.toInt,
      email = email,
      password = password,
      name = name,
      isAdmin = false
    )
  }

  def destroy() = TODO

}
