package id.idn.fahru.beritaapp.model.local

import id.idn.fahru.beritaapp.helpers.RecyclerViewTypes
import id.idn.fahru.beritaapp.model.remote.ArticlesItem

/**
 * Created by Imam Fahrur Rofi on 04/06/2020.
 */
data class CountryNewsTag(
    var country: String,
    var newsTag: String,
    var recyclerViewTypes: RecyclerViewTypes,
    var listNews: List<ArticlesItem>
)