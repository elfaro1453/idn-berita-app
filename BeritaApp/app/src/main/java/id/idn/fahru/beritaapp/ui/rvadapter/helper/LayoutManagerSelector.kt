package id.idn.fahru.beritaapp.ui.rvadapter.helper

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.helpers.RecyclerViewTypes
import id.idn.fahru.beritaapp.model.local.CountryNewsTag

/**
 * Created by Imam Fahrur Rofi on 05/06/2020.
 */
object LayoutManagerSelector {
    fun select(countryNewsTag: CountryNewsTag, context: Context): RecyclerView.LayoutManager {
        return when (countryNewsTag.recyclerViewTypes) {
            RecyclerViewTypes.TOP_HEADLINE_HORIZONTAL -> {
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            else -> LinearLayoutManager(context)
        }
    }
}