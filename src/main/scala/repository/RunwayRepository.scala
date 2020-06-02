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
}
