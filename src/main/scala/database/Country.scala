package scalain.database

/** Country representation in the DB. */
class Country(
    val code: String,
    val name: String,
    val keywords: Option[String]
) {
  def this() = this("", "", None)

  override def toString() = {
    val keywords_strings = keywords.getOrElse("No keywords")
    s"$code ($name), $keywords_strings"
  }
}
