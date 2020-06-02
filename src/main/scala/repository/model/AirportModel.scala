package scalain.repository.model

case class AirportModel(
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
    val homeLink: Some[String],
    val wikipediaLink: Some[String],
    val keywords: Some[String]
)
