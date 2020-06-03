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

  test("getMostAirports size 1") {
    implicit val session = Database.start()
    val countryRepo = new CountryRepository
    val airportRepo = new AirportRepository

    val country1 = new Country("FR", "France", None)
    val country2 = new Country("US", "United States", None)

    airportRepo.postAirport(new Airport("0", "FR_1", "FR"))
    airportRepo.postAirport(new Airport("0", "FR_2", "FR"))
    airportRepo.postAirport(new Airport("0", "FR_3", "FR"))
    airportRepo.postAirport(new Airport("0", "US_1", "US"))

    val res = countryRepo.getMostAirports(1)

    assert(res.length == 1)
    assert(res.lift(0).get.toString.equals(country1.toString))

    Database.stop
  }

  test("getMostAirports size 3") {
    implicit val session = Database.start()
    val countryRepo = new CountryRepository
    val airportRepo = new AirportRepository

    val country1 = new Country("FR", "France", None)
    val country2 = new Country("US", "United States", None)
    val country3 = new Country("UK", "United Kingdom", None)
    val country4 = new Country("DE", "Germany", None)
    val country5 = new Country("ES", "Spain", None)
    val country6 = new Country("PL", "Poland", None)

    airportRepo.postAirport(new Airport("0", "FR_1", "FR"))
    airportRepo.postAirport(new Airport("0", "FR_2", "FR"))
    airportRepo.postAirport(new Airport("0", "FR_3", "FR"))
    airportRepo.postAirport(new Airport("0", "FR_4", "FR"))
    airportRepo.postAirport(new Airport("0", "US_1", "US"))
    airportRepo.postAirport(new Airport("0", "UK_1", "UK"))
    airportRepo.postAirport(new Airport("0", "UK_2", "UK"))
    airportRepo.postAirport(new Airport("0", "UK_3", "UK"))
    airportRepo.postAirport(new Airport("0", "PL_1", "PL"))
    airportRepo.postAirport(new Airport("0", "PL_2", "PL"))
    airportRepo.postAirport(new Airport("0", "ES_1", "US"))

    val res = countryRepo.getMostAirports(1)

    assert(res.length == 3)
    assert(res.lift(0).get.toString.equals(country1.toString))
    assert(res.lift(0).get.toString.equals(country3.toString))
    assert(res.lift(0).get.toString.equals(country6.toString))

    Database.stop
  }
}
