package scalain.repository

import scalain.database.Database
import scalain.database.Country

import org.squeryl.PrimitiveTypeMode._

class CountryRepository(implicit val session: org.squeryl.Session) {
  def putCountry(country: Country) {
    using(session) {
      Database.Tables.countries.insert(country)
    }
  }
}
