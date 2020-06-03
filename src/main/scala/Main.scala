package scalain

import scala.io.StdIn.readLine
import scalain.parser._
import scalain.database.Database
import scalain.service._
import org.squeryl.PrimitiveTypeMode._

object Main extends App {

  /** Start database **/
  implicit val session = Database.start()
  /** Parse the resources files **/
  Parser.include_resources(session)

  /** Instantiate the services **/
  val service = new Service

  println("Use one of the following commands")

  while (true) {
    println("\"query\": display a country's airports and runways")
    println("\"reports\": display statistics")
    println("\"quit\": quit the program")

    /** Read input on stdin **/
    val entry = readLine()

    if (entry == "query")
    {
      println("QUERY") // Handle QUERY
    }
    else if (entry == "reports")
    {
      service.displayTypeRunwayPerCountry()
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

  session.close
}
