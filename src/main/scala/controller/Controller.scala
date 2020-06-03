package scalain.controller

import scalain.database._
import scalain.service.Service

/** Controller class managing the various services. */
class Controller(implicit val session: org.squeryl.Session) {
  val service = new Service

  def displayAirportsAndRunways(country: String) {
    val list = service.getAirportsAndRunways(country)

    if (list.isEmpty)
      println(s"No airports found for $country")
    else
      list.foreach(tup => {
        print(s"${tup._1}")
        tup._2.foreach(r => print(s" $r"))
        println()
      })
  }

  def displayRunwayTypePerCountry() = {
    println("SURFACES OF RUNWAYS IN EACH COUNTRY")

    print("Processing...")

    val runwayTypes = service.getRunwayTypePerCountry

    println(" Done.")

    runwayTypes.foreach(t => {
      print(s"${t._1}:")
      t._2.foreach(s => print(s" $s"))
      println()
    })
  }
}
