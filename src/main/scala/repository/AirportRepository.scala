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
}
