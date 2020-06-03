package scalain.test

import scalain.database._
import scalain.repository._
import org.squeryl.PrimitiveTypeMode._

class DatabaseTest extends org.scalatest.FunSuite {
  test("basic insert") {
    implicit val session = Database.start

    using(session) {
      val country = new Country("FR", "France", None)
      Database.Tables.countries
        .insert(country)

      val res =
        from(Database.Tables.countries)(s => select(s)).headOption.get.toString

      assert(country.toString.equals(res))
    }

    Database.stop
  }

  test("double insert") {
    implicit val session = Database.start

    using(session) {
      val country1 = new Country("FR", "France", None)
      val country2 = new Country("US", "United States", None)

      Database.Tables.countries
        .insert(country1)
      Database.Tables.countries
        .insert(country2)

      val res =
        from(Database.Tables.countries)(s => select(s)).toList

      assert(res.length == 2)
      assert(country1.toString.equals(res.lift(0).get.toString))
      assert(country2.toString.equals(res.lift(1).get.toString))
    }

    Database.stop
  }

  test("getCountryFromCode simple") {
    implicit val session = Database.start()
    val countryRepo = new CountryRepository

    val country = new Country("FR", "France", None)

    countryRepo.postCountry(country)

    val res = countryRepo.getCountryFromCode("FR")

    assert(!res.isEmpty)
    assert(res.get.toString.equals(country.toString))

    Database.stop
  }

  test("getCountryFromCode empty") {
    implicit val session = Database.start()
    val countryRepo = new CountryRepository

    val res = countryRepo.getCountryFromCode("FR")

    assert(res.isEmpty)

    Database.stop
  }

  test("getCountryFromCode not found") {
    implicit val session = Database.start()
    val countryRepo = new CountryRepository

    val country = new Country("FR", "France", None)

    countryRepo.postCountry(country)

    val res = countryRepo.getCountryFromCode("US")

    assert(res.isEmpty)

    Database.stop
  }
}
