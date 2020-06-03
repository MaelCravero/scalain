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

  test("getCountryFromName simple") {
    implicit val session = Database.start()
    val countryRepo = new CountryRepository

    val country = new Country("FR", "France", None)

    countryRepo.postCountry(country)

    val res = countryRepo.getCountryFromName("France")

    assert(!res.isEmpty)
    assert(res.get.toString.equals(country.toString))

    Database.stop
  }

  test("getAirports basic") {
    implicit val session = Database.start()

    val countryRepo = new CountryRepository
    val airportRepo = new AirportRepository

    val airport1 = new Airport("1", "FR_1", "FR")
    val airport2 = new Airport("2", "FR_2", "FR")
    val airport3 = new Airport("3", "US_1", "US")

    airportRepo.postAirport(airport1)
    airportRepo.postAirport(airport2)
    airportRepo.postAirport(airport3)

    val res = countryRepo.getAirports("FR")

    assert(res.length == 2)
    assert(res.lift(0).get.toString.equals(airport1.toString))
    assert(res.lift(1).get.toString.equals(airport2.toString))

    Database.stop
  }

  test("getRunways basic") {
    implicit val session = Database.start()

    val airportRepo = new AirportRepository
    val runwayRepo = new RunwayRepository

    val runway1 = new Runway("0", "0", "surface", None)
    val runway2 = new Runway("1", "0", "surface", None)
    val runway3 = new Runway("2", "1", "surface", None)

    runwayRepo.postRunway(runway1)
    runwayRepo.postRunway(runway2)
    runwayRepo.postRunway(runway3)

    val res = airportRepo.getRunways("0")

    assert(res.length == 2)
    assert(res.lift(0).get.toString.equals(runway1.toString))
    assert(res.lift(1).get.toString.equals(runway2.toString))

    Database.stop
  }

}
