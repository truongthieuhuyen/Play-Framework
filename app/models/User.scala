package models

import scalikejdbc._

case class User(userId: Int, email: String, password: String, name: String, isAdmin: Boolean) {
//  def save()(implicit session: DBSession = User.autoSession): Unit

//  def destroy()(implicit session: DBSession = User.autoSession): Unit
}

object User extends SQLSyntaxSupport[User] {
  override val tableName = "user"
  override val columns = Seq("user_id", "email", "password", "name", "is_admin")

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
    /** val idClause = sqls"where id = ${id}" */
    sql"select * from ${User.as(ut)} where user_id = ${userId}"
      .map(rs => User(ut.resultName)(rs))
      .single().apply()
  }

  def find(userId: Int)(implicit session: DBSession = AutoSession): Option[User] = {
    withSQL {
      select
        .from(User as ut)
        .where.eq(ut.userId, userId)
    }.map(User(ut.resultName)).single().apply()
  }

  def findAll()(implicit session: DBSession = AutoSession): List[User] = {
    withSQL(select.from(User as ut)).map(User(ut.resultName)).list().apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = AutoSession): List[User] = {
    withSQL {
      select.from(User as ut).where.append(sqls"${where}")
    }.map(User(ut.resultName)).list().apply()
  }

  def create(email: String, password: String, name: String, isAdmin: Boolean)
            (implicit session: DBSession = AutoSession): User = {
    val generatedKey = withSQL {
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

  def save(entity: User)(implicit session: DBSession = AutoSession): User = {
    withSQL {
      update(User).set(
        column.userId -> entity.userId,
        column.email -> entity.email,
        column.password -> entity.password,
        column.name -> entity.name,
        column.isAdmin -> entity.isAdmin
      ).where.eq(column.userId, entity.userId)
    }.update().apply()
    entity
  }

  def destroy(entity: User)(implicit session: DBSession = AutoSession): Int = {
    withSQL {
      delete.from(User).where.eq(column.userId, entity.userId)
    }.update().apply()
  }

}
