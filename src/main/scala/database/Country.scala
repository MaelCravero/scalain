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

  override def toString() = {
    val keywords_strings = keywords.getOrElse("No keywords")
    s"$id $code ($name), $continent - $wikipediaLink - $keywords_strings"
  }
}
