package id.idn.fahru.beritaapp.ui.rvadapter.helper

import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.helpers.RecyclerViewTypes
import id.idn.fahru.beritaapp.model.local.CountryNewsTag
import id.idn.fahru.beritaapp.ui.rvadapter.ItemHeadlineAdapter
import id.idn.fahru.beritaapp.ui.rvadapter.ItemMainAdapter

/**
 * Created by Imam Fahrur Rofi on 05/06/2020.
 */
object AdapterSelector {
    fun select(item: CountryNewsTag, size: Int): RecyclerView.Adapter<*> {
        return when (item.recyclerViewTypes) {
            RecyclerViewTypes.MAIN_TOP_HEADLINES -> {
                if (size == 1) ItemHeadlineAdapter() else ItemMainAdapter(size)
            }
            RecyclerViewTypes.RV_MAIN -> ItemMainAdapter(size)
            else -> ItemHeadlineAdapter()
        }
    }
}