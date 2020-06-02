package scalain.repository.model

case class RunwayModel(
    val id: Int,
    val airportRef: Int,
    val airportIdent: String,
    val length: Int,
    val width: Int,
    val surface: String,
    val lighted: Boolean,
    val closed: Boolean,
    val leIdent: String
)