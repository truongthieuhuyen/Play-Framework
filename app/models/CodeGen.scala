package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.MySQLProfile",
    "org.mysql.Driver",
    "jdbc:mysql://localhost/task_list?user=root&password=duongmich",
//    "D:\\myDocuments\\Scala\\intern\\play-framework-starter-package\\app",
    "models", None, None, true, false
  )
}
