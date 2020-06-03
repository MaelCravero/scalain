package scalain.database

/** Airport representation in the DB. */
class Airport(
    val id: String,
    val name: String,
    val isoCountry: String
) {
  def this() =
    this(
      "",
      "",
      ""
    )

  override def toString(): String = s"$id - $name ($isoCountry)"
}
