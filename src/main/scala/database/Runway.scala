package scalain.database

/** Runway representation in the DB. */
class Runway(
    val id: String,
    val airportRef: String,
    val surface: String,
    val leIdent: Option[String]
) {
  def this() =
    this(
      "",
      "",
      "",
      None
    )

  override def toString(): String = {
    val ident = if (leIdent.isEmpty) "{no lat}" else leIdent.get
    s"$id ($airportRef) - $surface - $ident"
  }
}
