package infrastructures

import slick.codegen.SourceCodeGenerator

object SlickCodegen {

  def main(args: Array[String]): Unit = {

    val profile = "slick.jdbc.H2Profile"
    val jdbcDriver = "org.h2.Driver"
    val url = "jdbc:h2:./h2/h2db"
    val user = "sa"
    val password = ""

    val outputFolder = "app"
    val pkg = "persistence.models"

    // change from slickDriver to profile in version 3.2.0
    SourceCodeGenerator.main(
      Array(profile, jdbcDriver, url, outputFolder, pkg, user, password)
    )
  }
}

