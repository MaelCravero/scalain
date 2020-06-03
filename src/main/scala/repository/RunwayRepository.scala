package scalain.repository

import scalain.database.Database
import scalain.database.Runway

import org.squeryl.PrimitiveTypeMode._

/** RunwayRepository class managing requests to the DB. */
class RunwayRepository(implicit val session: org.squeryl.Session) {

  /** Add a new runway to the the RUNWAYS table. */
  def postRunway(runway: Runway) {
    using(session) {
      Database.Tables.runways.insert(runway)
    }
  }

  /** Get a airport from the AIRPORTS table by its ID. */
  def getRunwayFromId(id: String): Option[Runway] = {
    using(session) {
      from(Database.Tables.runways)(select(_)).where(_.id === id).headOption
    }
  }

  /** Get most common runway latitudes. */
  def getMostCommonLatitudes(n: Int) {
    using(session) {
      //from(Database.Tables.runways)(select(_))
    }
  }
}
