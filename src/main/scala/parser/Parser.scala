package scalain.parser

import scala.io.Source
import scala.collection.immutable.List
import scalain.database._
import scalain.repository._

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

    parse_resource("resources/airports.csv").map({ array =>
      Database.Tables.airports
              .insert(new Airport(array(0).toInt,
                                  array(1),
                                  array(2),
                                  array(3),
                                  array(4).toDouble,
                                  array(5).toDouble,
                                  array(6).toInt,
                                  array(7),
                                  array(8),
                                  array(9),
                                  array(10),
                                  array(11),
                                  array(12),
                                  array(13),
                                  array(14),
                                  if (array(15) == "") None else Some(array(15)),
                                  if (array(16) == "") None else Some(array(16)),
                                  if (array(17) == "") None else Some(array(17))))
    })

    parse_resource("resources/countries.csv").map({ array =>
      Database.Tables.countries
              .insert(new Country(array(0).toInt,
                                  array(1),
                                  array(2),
                                  array(3),
                                  array(4),
                                  if (array(5) == "") None else Some(array(5))))
    })

    parse_resource("resources/runways.csv").map({ array =>
      Database.Tables.runways
              .insert(new Runway(array(0).toInt,
                                 array(1).toInt,
                                 array(2),
                                 array(3).toInt,
                                 array(4).toInt,
                                 array(5),
                                 array(6) == "1",
                                 array(7) == "1",
                                 array(8)))
    })
  }

}
