package id.idn.fahru.beritaapp.ui.rvadapter

import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.helpers.RecyclerViewTypes
import id.idn.fahru.beritaapp.model.local.CountryNewsTag

/**
 * Created by Imam Fahrur Rofi on 05/06/2020.
 */
object AdapterSelector {
    fun select(countryNewsTag: CountryNewsTag, size: Int): RecyclerView.Adapter<*> {
        return when (countryNewsTag.recyclerViewTypes) {
            RecyclerViewTypes.MAIN_TOP_HEADLINES -> {
                if (size == 1) ItemHeadlineAdapter(
                    1,
                    RecyclerViewTypes.MAIN_TOP_HEADLINES
                ) else ItemMainAdapter(size)
            }
            RecyclerViewTypes.RV_MAIN -> ItemMainAdapter(size)
            else -> ItemHeadlineAdapter(
                size,
                RecyclerViewTypes.TOP_HEADLINE
            )
        }
    }
}