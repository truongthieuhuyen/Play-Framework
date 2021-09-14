package models

import scalikejdbc._

case class Item(itemId: Int,itemName: String, userId: Int, isValid: Boolean)
object TaskListTable extends SQLSyntaxSupport[Item]{
  override val tableName = "item"

  def apply(e: ResultName[Item])(rs: WrappedResultSet): Item =
    new Item(
      rs.int("id"),
      rs.string("itemName"),
      rs.int("userId"),
      rs.boolean("isValid")
    )


}
