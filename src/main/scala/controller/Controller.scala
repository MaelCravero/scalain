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

  def displayMostAirports() = {
    val list = service.getAirportsPerCountryNumber

    val printElt = (t: (String, Int)) => println(s"${t._1} - ${t._2} airports")

    println("MOST AIRPORTS:")
    list.take(10).foreach(printElt)

    println("LEAST AIRPORTS")
    list.reverse.take(10).foreach(printElt)
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

  def displayMostCommonRunwayLatitudes(): Unit = {
    println("10 MOST COMMON RUNWAY LATTITUDES")

    print("Processing...")

    val latitudes = service.getMostCommonRunwayLatitudes

    println(" Done.")

    latitudes.foreach(t => println(s"${t._1}: ${t._2.toString}"))
  }
}
