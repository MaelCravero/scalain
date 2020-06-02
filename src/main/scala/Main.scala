package scalain

import scala.io.StdIn.readLine
import scalain.parser._
import scalain.database.Database

object Main extends App {

  val database = Database.start
  Parser.include_resources(database)

  println("Use one of the following commands")

  while (true) {
      println("\"query\": display a country's airports and runways")
      println("\"reports\": display statistics")
      println("\"quit\": quit the program")

    val entry = readLine

    println(entry)

    if (entry == "query")
    {
      println("QUERY") // Handle QUERY
    }
    else if (entry == "reports")
    {
      println("REPORTS") // Handle REPORTS
    }
    else if (entry == "quit")
    {
      System.exit(0)
    }
    else
    {
      println("Not a valid command, please use one of the following:")
    }
  }
}
