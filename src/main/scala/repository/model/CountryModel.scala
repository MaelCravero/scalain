package scalain.repository.model

case class CountryModel(
    val id: Int,
    val code: String,
    val name: String,
    val continent: String,
    val wikipediaLink: String,
    val keywords: Some[String]
)
