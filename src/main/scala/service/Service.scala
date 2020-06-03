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
      airports.map({ airport =>
        (airport.isoCountry, runways.filter(runway => runway.airportRef == airport.id)
                                    .map(runway => runway.surface))
      })

    val runwaysTypesPerCountry =
      countries.map({ country =>
        (country.name, runwaysTypesPerCountryCodes.filter(tup => tup._1 == country.code)
                                                  .flatMap(tup => tup._2))
      })

    runwaysTypesPerCountry.foreach({ tup =>
      print(tup._1.concat(":"))
      tup._2.foreach(surface => print(surface.concat(" ")))
      print("\n")
    })
  }

  def getMostCommonRunwayLatitudes() : List[(String, Int)] = {
    runwayRepo.getAllRunways
              .filter(runway => runway.leIdent != None)
              .groupBy(_.leIdent)
              .map(tup => (tup._1.get, tup._2.length)).toList
              .sortWith((el1, el2) => el1._2 > el2._2)
              .take(10)
  }
}
