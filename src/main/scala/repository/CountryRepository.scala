package scalain.repository

import scalain.database.Database
import scalain.database.Country
import scalain.database.Airport

import org.squeryl.PrimitiveTypeMode._

/** CountryRepository class managing requests to the DB. */
class CountryRepository(implicit val session: org.squeryl.Session) {

  /** Add a new country to the the COUNTRIES table. */
  def postCountry(country: Country) {
    using(session) {
      Database.Tables.countries.insert(country)
    }
  }

  /** Get all countries from the COUNTRIES table as a list. */
  def getAllCountries(): List[Country] = {
    using(session) {
      from(Database.Tables.countries)(select(_)).toList
    }
  }

  /** Get a country from the COUNTRIES table by its ID. */
  def getCountryFromCode(code: String): Option[Country] = {
    using(session) {
      from(Database.Tables.countries)(select(_))
        .where(_.code === code)
        .headOption
    }
  }

  /** Get a country from the COUNTRIES table by its ID. */
  def getCountryFromName(name: String): Option[Country] = {
    using(session) {
      from(Database.Tables.countries)(select(_))
        .where(_.name === name)
        .headOption
    }
  }

  /** Get all airports in a given country. */
  def getAirports(countryCode: String): List[Airport] = {
    using(session) {
      from(Database.Tables.airports)(select(_))
        .where(_.isoCountry === countryCode)
        .toList
    }
  }

}
