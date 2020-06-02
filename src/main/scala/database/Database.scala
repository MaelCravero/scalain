package scalain.database

import scalain.database._

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter

/** Database class regrouping all DB setup. */
object Database {
  Class.forName("org.h2.Driver")

  /** The url of the DB. */
  private val url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"

  /** Create a connection to the DB and initialize its tables. */
  def start(): org.squeryl.Session = {
    val session =
      Session.create(java.sql.DriverManager.getConnection(url), new H2Adapter)

    using(session) {
      Tables.create
    }

    session
  }

  /** Tables class containing the tables of the DB. */
  object Tables extends Schema {

    /** Table for countries. */
    val countries = table[Country]("COUNTRIES")

    /** Table for airports. */
    val airports = table[Airport]("AIRPORTS")

    /** Table for runways. */
    val runways = table[Runway]("RUNWAY")

    /** Display the creation request of the tables. */
    def dump(implicit session: org.squeryl.Session) =
      using(session) {
        Database.Tables.printDdl
      }

  }
}
