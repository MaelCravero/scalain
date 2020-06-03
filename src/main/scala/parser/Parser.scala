package scalain.parser

import scala.io.Source
import scala.collection.immutable.List
import scalain.database._
import scalain.repository._
import org.squeryl.PrimitiveTypeMode._

object Parser {

  /** Read the content and return the content of a file **/
  def read_file(path: String) : Iterator[String] = {
    val source = Source.fromFile(path)
    source.getLines
  }


  def parse_resource(path : String) = {
    val lines = read_file(path)
    lines.next
    lines.map(line => line.split(','))
  }

  def parse_airports() = {
    parse_resource("resources/airports.csv").map({ array =>
      new Airport(array(0), array(3), array(8))
    })
  }

  def parse_countries() = {
    parse_resource("resources/countries.csv").map({ array =>
      new Country(array(1), array(2), if (array.length <= 5) None else Some(array(5)))
    })
  }

  def parse_runways() = {
    var c = 0
    parse_resource("resources/runways.csv").map({ array =>
      new Runway(array(0), array(1), array(5), if (array.length <= 8) None else Some(array(8)))
    })
  }

  def include_resources(session : org.squeryl.Session) : Unit = {
    using (session) {
      parse_airports().foreach(Database.Tables.airports.insert)
      parse_countries().foreach(Database.Tables.countries.insert)
      parse_runways().foreach(Database.Tables.runways.insert)
    }
  }

}
