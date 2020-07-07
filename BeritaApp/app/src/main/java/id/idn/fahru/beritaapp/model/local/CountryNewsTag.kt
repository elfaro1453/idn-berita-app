package id.idn.fahru.beritaapp.model.local

import android.os.Parcelable
import id.idn.fahru.beritaapp.helpers.RecyclerViewTypes
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import kotlinx.android.parcel.Parcelize

/**
 * Created by Imam Fahrur Rofi on 04/06/2020.
 */
@Parcelize
data class CountryNewsTag(
    var country: String,
    var newsTag: String,
    var recyclerViewTypes: RecyclerViewTypes,
    var listNews: List<ArticlesItem>
) : Parcelable