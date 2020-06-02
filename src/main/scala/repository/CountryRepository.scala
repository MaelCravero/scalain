package scalain.repository

import scalain.repository.model.CountryModel
import scalain.database.Database
import scalain.database.Country

import org.squeryl.PrimitiveTypeMode._

class CountryRepository(implicit val session: org.squeryl.Session) {
  def putCountry(country: CountryModel) {
    using(session) {
      Database.Tables.countries.insert(
        new Country(
          country.id,
          country.code,
          country.name,
          country.continent,
          country.wikipediaLink,
          country.keywords
        )
      )
    }
  }
}
