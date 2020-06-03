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

  def include_resources(session : org.squeryl.Session) : Unit = {

    using (session) {
      parse_resource("resources/airports.csv").map({ array =>
        Database.Tables.airports.insert(new Airport(array(0), array(3), array(8)))
      })

      parse_resource("resources/countries.csv").map({ array =>
        Database.Tables.countries
                .insert(new Country(array(1), array(2),
                                    if (array(5) == "") None else Some(array(5))))
      })

      parse_resource("resources/runways.csv").map({ array =>
        Database.Tables.runways
                .insert(new Runway(array(0), array(1), array(5), array(8)))
      })
    }
  }

}
