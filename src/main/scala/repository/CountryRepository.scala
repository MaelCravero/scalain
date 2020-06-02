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
}
