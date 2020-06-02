package scalain.database

/** Airport representation in the DB. */
class Airport(
    val id: Int,
    val ident: String,
    val airportType: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Int,
    val continent: String,
    val isoCountry: String,
    val isoRegion: String,
    val municipality: String,
    val scheduledService: String,
    val gpsCode: String,
    val iataCode: String,
    val localCode: String,
    val homeLink: Option[String],
    val wikipediaLink: Option[String],
    val keywords: Option[String]
) {
  def this() =
    this(
      0,
      "",
      "",
      "",
      0,
      0,
      0,
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      None,
      None,
      None
    )
}
