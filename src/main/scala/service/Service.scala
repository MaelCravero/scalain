package scalain.service

import scalain.database._
import scalain.repository._

/** Service class responding to the requests **/
class Service(implicit val session: org.squeryl.Session) {

  /** Country repository of the service. */
  private val countryRepo = new CountryRepository

  /** Airport repository of the service. */
  private val airportRepo = new AirportRepository

  /** Runway repository of the service. */
  private val runwayRepo = new RunwayRepository

  /** Get all airports and runways of a given country. */
  def getAirportsAndRunways(
      country: String
  ): List[(String, List[String])] = {
    val countryCode: Option[String] = country match {
      case c if !countryRepo.getCountryFromCode(c).isEmpty =>
        Some(c)
      case c if countryRepo.getCountryFromName(c).isEmpty =>
        None
      case _ =>
        Some(countryRepo.getCountryFromName(country).get.code)
    }

    if (countryCode.isEmpty)
      Nil
    else
      countryRepo
        .getAirports(countryCode.get)
        .map(a => (a.name, airportRepo.getRunways(a.id).map(r => r.id)))
  }

  /** Get the number of airports of each country. */
  def getAirportsPerCountryNumber(): List[(String, Int)] = {
    countryRepo
      .getAllCountries()
      .map(c => (c.name, countryRepo.getAirports(c.code).length))
      .sortWith(_._2 > _._2)
  }

  /** Get the runway types in each country. */
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

  /** Get the most common latitudes of runways. */
  def getMostCommonRunwayLatitudes(n: Int): List[(String, Int)] = {
    runwayRepo.getAllRunways
      .filter(runway => runway.leIdent != None)
      .groupBy(_.leIdent)
      .map(tup => (tup._1.get, tup._2.length))
      .toList
      .sortWith((el1, el2) => el1._2 > el2._2)
      .take(n)
  }
}
