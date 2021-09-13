//package models
//// AUTO-GENERATED Slick data model
///** Stand-alone Slick data model for immediate use */
//object Tables extends {
//  val profile = slick.jdbc.MySQLProfile
//} with Tables
//
///** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
//trait Tables {
//  val profile: slick.jdbc.JdbcProfile
//  import profile.api._
//  import slick.model.ForeignKeyAction
//  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
//  import slick.jdbc.{GetResult => GR}
//
//  /** DDL for all tables. Call .create to execute. */
//  lazy val schema: profile.SchemaDescription = Items.schema ++ Users.schema
//  @deprecated("Use .schema instead of .ddl", "3.0")
//  def ddl = schema
//
//  /** Entity class storing rows of table Items
//   *  @param itemId Database column item_id SqlType(INT), PrimaryKey
//   *  @param userId Database column user_id SqlType(INT)
//   *  @param text Database column text SqlType(VARCHAR), Length(2000,true) */
//  case class ItemsRow(itemId: Int, userId: Int, text: String)
//  /** GetResult implicit for fetching ItemsRow objects using plain SQL queries */
//  implicit def GetResultItemsRow(implicit e0: GR[Int], e1: GR[String]): GR[ItemsRow] = GR{
//    prs => import prs._
//    ItemsRow.tupled((<<[Int], <<[Int], <<[String]))
//  }
//  /** Table description of table items. Objects of this class serve as prototypes for rows in queries. */
//  class Items(_tableTag: Tag) extends profile.api.Table[ItemsRow](_tableTag, Some("task_list"), "items") {
//    def * = (itemId, userId, text) <> (ItemsRow.tupled, ItemsRow.unapply)
//    /** Maps whole row to an option. Useful for outer joins. */
//    def ? = ((Rep.Some(itemId), Rep.Some(userId), Rep.Some(text))).shaped.<>({r=>import r._; _1.map(_=> ItemsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
//
//    /** Database column item_id SqlType(INT), PrimaryKey */
//    val itemId: Rep[Int] = column[Int]("item_id", O.PrimaryKey)
//    /** Database column user_id SqlType(INT) */
//    val userId: Rep[Int] = column[Int]("user_id")
//    /** Database column text SqlType(VARCHAR), Length(2000,true) */
//    val text: Rep[String] = column[String]("text", O.Length(2000,varying=true))
//
//    /** Foreign key referencing Users (database name user_id) */
//    lazy val usersFk = foreignKey("user_id", userId, Users)(r => r.userId, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
//  }
//  /** Collection-like TableQuery object for table Items */
//  lazy val Items = new TableQuery(tag => new Items(tag))
//
//  /** Entity class storing rows of table Users
//   *  @param userId Database column user_id SqlType(INT), PrimaryKey
//   *  @param username Database column username SqlType(VARCHAR), Length(20,true)
//   *  @param password Database column password SqlType(VARCHAR), Length(200,true) */
//  case class UsersRow(userId: Int, username: String, password: String)
//  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
//  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String]): GR[UsersRow] = GR{
//    prs => import prs._
//    UsersRow.tupled((<<[Int], <<[String], <<[String]))
//  }
//  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
//  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, Some("task_list"), "users") {
//    def * = (userId, username, password) <> (UsersRow.tupled, UsersRow.unapply)
//    /** Maps whole row to an option. Useful for outer joins. */
//    def ? = ((Rep.Some(userId), Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
//
//    /** Database column user_id SqlType(INT), PrimaryKey */
//    val userId: Rep[Int] = column[Int]("user_id", O.PrimaryKey)
//    /** Database column username SqlType(VARCHAR), Length(20,true) */
//    val username: Rep[String] = column[String]("username", O.Length(20,varying=true))
//    /** Database column password SqlType(VARCHAR), Length(200,true) */
//    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
//  }
//  /** Collection-like TableQuery object for table Users */
//  lazy val Users = new TableQuery(tag => new Users(tag))
//}
