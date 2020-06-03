package scalain.repository

import scalain.database.Database
import scalain.database.Airport

import org.squeryl.PrimitiveTypeMode._

/** AirportRepository class managing requests to the DB. */
class AirportRepository(implicit val session: org.squeryl.Session) {

  /** Add a new airport to the AIRPORTS table. */
  def postAirport(airport: Airport) {
    using(session) {
      Database.Tables.airports.insert(airport)
    }
  }

  /** Get a airport from the AIRPORTS table by its ID. */
  def getAirportFromCode(id: String): Option[Airport] = {
    using(session) {
      from(Database.Tables.airports)(select(_)).where(_.id === id).headOption
    }
  }

}
