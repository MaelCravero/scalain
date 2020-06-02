package scala.main.parser

import scala.io.Source
import scala.collection.immutable.List

object Parser {

  def read_file(path: String) : Iterator[String] = {
    val source = Source.fromFile(path)
    source.getLines
  }

  def parse_resources() = {
    val airports = read_file("resources/airports.csv")
    airports.next
    val countries = read_file("resources/countries.csv")
    countries.next
    val runways = read_file("resources/runways.csv")
    runways.next

    val stripped_airports = airports.map(line => line.split(','))
    val stripped_countries = countries.map(line => line.split(','))
    val stripped_runways = runways.map(line => line.split(','))
  }

}
