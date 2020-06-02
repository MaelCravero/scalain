package scalain.database

import scalain.database._

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter

object Database {
  Class.forName("org.h2.Driver")

  private val url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
  private val connection = java.sql.DriverManager.getConnection(url)

  def start(): org.squeryl.Session = {
    val session = Session.create(connection, new H2Adapter)

    using(session) {
      Tables.create
    }

    session
  }

  object Tables extends Schema {
    val countries = table[Country]("COUNTRIES")
    val airports = table[Airport]("AIRPORTS")
    val runways = table[Runway]("RUNWAY")

    def dump(implicit session: org.squeryl.Session) =
      using(session) {
        Database.Tables.printDdl
      }

  }
}
