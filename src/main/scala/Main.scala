package scalain

import scala.io.StdIn.readLine
import scalain.parser._
import scalain.database.Database
import scalain.controller.Controller
import org.squeryl.PrimitiveTypeMode._

/** Main file of the program. */
object Main extends App {

  /** Connection to the database. **/
  implicit val session = Database.start()

  /** Parse the resources files **/
  print("Parsing resources...")
  Parser.include_resources(session)
  println(" Done.")

  /** Controller managing the various services. **/
  private val controller = new Controller

  /** Parse the user input to the program. */
  def parseUserInput() {
    println("\"query\": display a country's airports and runways")
    println("\"reports\": display statistics")
    println("\"quit\": quit the program")

    /** Read input on stdin **/
    val entry = readLine()

    entry match {
      case "query" => {
        queryInput()
        parseUserInput()
      }
      case "reports" => {
        printReports()
        parseUserInput()
      }
      case "quit" => println("Exiting.")
      case _ => {
        println("Not a valid command, please use one of the following:")
        parseUserInput()
      }
    }
  }

  /** Function for the "query" option. */
  def queryInput() {
    println("Please input a country name or ISO code:")
    val country = readLine()

    controller.displayAirportsAndRunways(country)
  }

  /** Function for the "reports" option. */
  def printReports() {
    controller.displayMostAirports
    controller.displayMostCommonRunwayLatitudes
    controller.displayRunwayTypePerCountry
  }

  println("Use one of the following commands")

  parseUserInput()

  Database.stop
}
