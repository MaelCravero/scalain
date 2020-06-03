package scalain.repository

import scalain.database.Database
import scalain.database.Country

import org.squeryl.PrimitiveTypeMode._

/** CountryRepository class managing requests to the DB. */
class CountryRepository(implicit val session: org.squeryl.Session) {

  /** Add a new country to the the COUNTRIES table. */
  def postCountry(country: Country) {
    using(session) {
      Database.Tables.countries.insert(country)
    }
  }

  /** Get a country from the COUNTRIES table by its ID. */
  def getCountryFromId(id: Int): Option[Country] = {
    using(session) {
      from(Database.Tables.countries)(select(_)).where(_.id === id).headOption
    }
  }

  /** Get countries with highest number of airports. */
  def getMostAirports(n: Int): List[Country] = {
    using(session) {
      join(Database.Tables.countries, Database.Tables.airports)(
        (country, airport) =>
          select(country)
            .orderBy(count(airport.isoCountry))
            .on(country.code === airport.isoCountry)
      ).page(0, 10).toList
    }
  }

}
