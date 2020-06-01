package scalain.database

import scalain.database._

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter

object Database {
  Class.forName("org.h2.Driver")

  val url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
  val connection = java.sql.DriverManager.getConnection(url)

  def start() {
    SessionFactory.concreteFactory =
      Some(() => Session.create(connection, new H2Adapter))
  }

  object Tables extends Schema {
    val countries = table[Country]("COUNTRIES")
  }
}
