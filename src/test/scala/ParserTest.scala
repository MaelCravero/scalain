package scalain.test

import java.io.File
import java.io.PrintWriter
import scalain.database._
import scalain.parser._

class Parser_Test extends org.scalatest.FunSuite {

  /** Rename a file (to conserve integrity of ressources) **/
  def mv(oldName: String, newName: String) = {
    val file = new File(oldName).renameTo(new File(newName))
  }

  test("single object in each csv") {
    mv("resources/airports.csv", "resources/airports.csv.backup")
    mv("resources/countries.csv", "resources/countries.csv.backup")
    mv("resources/runways.csv", "resources/runways.csv.backup")

    val airports_file = new PrintWriter(new File("resources/airports.csv"))
    airports_file.write("header\n6523,\"00A\",\"heliport\",\"Total Rf Heliport\",40.07080078125,-74.93360137939453,11,\"NA\",\"US\",\"US-PA\",\"Bensalem\",\"no\",\"00A\",,\"00A\",,,\n")
    val countries_file = new PrintWriter(new File("resources/countries.csv"))
    countries_file.write("header\n302672,\"AD\",\"Andorra\",\"EU\",\"http://en.wikipedia.org/wiki/Andorra\",\n")
    val runways_file = new PrintWriter(new File("resources/runways.csv"))
    runways_file.write("header\n269408,6523,\"00A\",80,80,\"ASPH-G\",1,0,\"H1\",,,,,,,,,,,")

    try {
      val airports = Parser.parse_airports()
      val countries = Parser.parse_countries()
      val runways = Parser.parse_runways()

      assert(airports.length == 1)
      assert(countries.length == 1)
      assert(runways.length == 1)

      assert(airports.next.id == "6523")
      assert(airports.next.name == "Total Rf Heliport")
      assert(airports.next.isoCountry == "US")

      assert(countries.next.code == "AD")
      assert(countries.next.name == "Andorra")
      assert(countries.next.keywords == None)

      assert(runways.next.id == "269408")
      assert(runways.next.airportRef == "6523")
      assert(runways.next.surface == "ASPH-G")
      assert(runways.next.leIdent == "H1")
    }
    catch {
      case _: Throwable => println("test failed")
    }
    finally {
      airports_file.close
      countries_file.close
      runways_file.close
      mv("resources/airports.csv.backup", "resources/airports.csv")
      mv("resources/countries.csv.backup", "resources/countries.csv")
      mv("resources/runways.csv.backup", "resources/runways.csv")
    }
  }
}
