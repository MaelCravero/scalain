package scalain.service

import scalain.database._
import scalain.repository._
import org.squeryl.PrimitiveTypeMode._

/** CountryService class responding to the requests **/
class Service(implicit val session: org.squeryl.Session) {

  val countryRepo = new CountryRepository
  val airportRepo = new AirportRepository
  val runwayRepo = new RunwayRepository

  def displayTypeRunwayPerCountry() : Unit = {
    val countries = countryRepo.getAllCountries
    val airports = airportRepo.getAllAirports
    val runways = runwayRepo.getAllRunways

    val runwaysTypesPerCountryCodes =
      airports.map({airport =>
        (airport.isoCountry, runways.filter(runway => runway.airportRef == airport.id))
      })
  }
}
