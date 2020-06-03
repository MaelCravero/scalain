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

  def getMostCommonRunwayLatitudes(): List[(String, Int)] = {
    runwayRepo.getAllRunways
      .filter(runway => runway.leIdent != None)
      .groupBy(_.leIdent)
      .map(tup => (tup._1.get, tup._2.length))
      .toList
      .sortWith((el1, el2) => el1._2 > el2._2)
      .take(10)
  }
}
