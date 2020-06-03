package scalain.repository

import scalain.database.Database
import scalain.database.Airport
import scalain.database.Runway

import org.squeryl.PrimitiveTypeMode._

/** AirportRepository class managing requests to the DB. */
class AirportRepository(implicit val session: org.squeryl.Session) {

  /** Add a new airport to the AIRPORTS table. */
  def postAirport(airport: Airport) {
    using(session) {
      Database.Tables.airports.insert(airport)
    }
  }

  /** Get all runways of a given airport. */
  def getRunways(airport: String): List[Runway] = {
    using(session) {
      from(Database.Tables.runways)(select(_))
        .where(_.airportRef === airport)
        .toList
    }
  }

  /** Get a airport from the AIRPORTS table by its ID. */
  def getAirportFromCode(id: String): Option[Airport] = {
    using(session) {
      from(Database.Tables.airports)(select(_)).where(_.id === id).headOption
    }
  }

  /** Get all airports from the AIRPORTS table as a list. */
  def getAllAirports(): List[Airport] = {
    using(session) {
      from(Database.Tables.airports)(select(_)).toList
    }
  }

}
