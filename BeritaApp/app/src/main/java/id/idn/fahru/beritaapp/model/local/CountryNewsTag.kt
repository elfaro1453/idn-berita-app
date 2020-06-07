package id.idn.fahru.beritaapp.model.local

/**
 * Created by Imam Fahrur Rofi on 04/06/2020.
 */
data class CountrySource (
    val country : String = "",
    val sources : List<NewsTag> = ArrayList<NewsTag>()
)

data class NewsTag (
    val newsTag : String = "",
    val recyclerViewTypes: RecyclerViewTypes ? = null
)

data class CountryNewsTag (
    var country: String = "",
    var newsTag: String = "",
    var recyclerViewTypes: RecyclerViewTypes? = null
)

enum class RecyclerViewTypes {
    RV_MAIN, MAIN_TOP_HEADLINES, TOP_HEADLINE, TOP_HEADLINE_VERTICAL
}