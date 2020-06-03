package scalain.database

/** Runway representation in the DB. */
class Runway(
    val id: String,
    val airportRef: String,
    val surface: String,
    val leIdent: String
)
