package scalain.controller

import scalain.database._
import scalain.service.Service

/** Controller class managing the various services. */
class Controller(implicit val session: org.squeryl.Session) {
  val service = new Service

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
