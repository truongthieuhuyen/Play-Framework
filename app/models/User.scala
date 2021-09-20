package models

import play.api.libs.json.Json
import scalikejdbc._

/** Pair to database */
case class User(userId: Int, email: String, password: String, name: String, isAdmin: Boolean) {
  def save()(implicit session: DBSession = User.autoSession): User = User.save(this)(session)

  def destroy()(implicit session: DBSession = User.autoSession): Unit = User.destroy(this)(session)
}

/** Mapping */
object User extends SQLSyntaxSupport[User] {
  override val tableName = "user"
  override val columns = Seq("user_id", "email", "password", "name", "is_admin")

  def apply(ut: SyntaxProvider[User])(rs: WrappedResultSet): User =
    apply(ut.resultName)(rs)

  def apply(ut: ResultName[User])(rs: WrappedResultSet): User =
    new User(
      userId = rs.get(ut.userId),
      email = rs.get(ut.email),
      password = rs.get(ut.password),
      name = rs.get(ut.name),
      isAdmin = rs.get(ut.isAdmin)
    )

  val ut = User.syntax("ut")
  implicit val newTodoListJson = Json.format[User]



//  val mda = MessageDigest.getInstance("SHA-512")
//  val cookieHeader = "X-Auth-Token"

//  private def createCookie(user: User): Cookie = {
//    val randomPart = UUID.randomUUID().toString.toUpperCase
//    val userPart = user.userId.toString.toUpperCase
//    val key = s"$randomPart|$userPart"
//    val token = Base64.encodeBase64String(mda.digest(key.getBytes))
//    val duration = Duration.create(10, TimeUnit.HOURS)
//    cacheApi.set(token, user, duration)
//    Cookie(cookieHeader, token, maxAge = Some(duration.toSeconds.toInt))
//  }

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
    withSQL(
      select.from(User as ut))
      .map(User(ut.resultName))
      .list().apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = AutoSession): List[User] = {
    withSQL {
      select.from(User as ut).where.append(sqls"${where}")
    }.map(User(ut.resultName)).list().apply()
  }

  def create(email: String, password: String, name: String, isAdmin: Boolean)
            (implicit session: DBSession = AutoSession): User = {
//    val generatedKey = withSQL {
//      insert.into(User).columns(
//        column.email,
//        column.password,
//        column.name,
//        column.isAdmin
//      ).values(
//        email,
//        password,
//        name,
//        isAdmin
//      )
//    }.updateAndReturnGeneratedKey().apply()

    val generatedKey = withSQL {
      insert.into(User).namedValues(
        column.email -> "alicer@gmail.com",
        column.password -> "12345678",
        column.name -> "Alice",
        column.isAdmin -> false
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
