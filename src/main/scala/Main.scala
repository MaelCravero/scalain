package scalain

import scala.io.StdIn.readLine
import scalain.parser._
import scalain.database.Database
import scalain.controller.Controller
import org.squeryl.PrimitiveTypeMode._

object Main extends App {

  /** Start database **/
  implicit val session = Database.start()

  /** Parse the resources files **/
  print("Parsing resources...")
  Parser.include_resources(session)
  println(" Done.")

  /** Instantiate the services **/
  val controller = new Controller

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

  def queryInput() {
    println("Please input a country name or ISO code:")
    val country = readLine()

    controller.displayAirportsAndRunways(country)
  }

  def printReports() {
    controller.displayRunwayTypePerCountry
  }

  println("Use one of the following commands")

  parseUserInput()

  session.close
}
