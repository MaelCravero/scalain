package scalain.database

class Country(
    val id: Int,
    val code: String,
    val name: String,
    val continent: String,
    val wikipediaLink: String,
    val keywords: Option[String]
) {
  def this() = this(0, "", "", "", "", None)
}
