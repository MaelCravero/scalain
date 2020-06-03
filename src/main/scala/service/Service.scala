package scalain.service

import scalain.database._
import scalain.repository._

/** Service class responding to the requests **/
class Service(implicit val session: org.squeryl.Session) {

  val countryRepo = new CountryRepository
  val airportRepo = new AirportRepository
  val runwayRepo = new RunwayRepository

  def getRunwayTypePerCountry(): List[(String, List[String])] = {
    countryRepo.getAllCountries.map { c =>
      (
        c.name,
        countryRepo
          .getAirports(c.code)
          .flatMap(a => airportRepo.getRunways(a.id).map(r => r.surface))
          .distinct
      )
    }

  }
}
