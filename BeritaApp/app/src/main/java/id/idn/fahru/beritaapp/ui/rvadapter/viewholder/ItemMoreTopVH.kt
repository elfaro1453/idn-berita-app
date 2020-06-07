package id.idn.fahru.beritaapp.ui.rvadapter.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.databinding.ItemMoreTopBinding
import id.idn.fahru.beritaapp.helpers.RecyclerViewTypes
import id.idn.fahru.beritaapp.model.local.CountryNewsTag
import id.idn.fahru.beritaapp.ui.rvadapter.helper.AdapterSelector

/**
 * Created by Imam Fahrur Rofi on 05/06/2020.
 */
class ItemMoreTop(
    private val binding: ItemMoreTopBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    private val txtTitle = binding.btnLoadMore.textTitle
    private val btnMore = binding.btnLoadMore.buttonMore
    private val recyclerView = binding.rvBottom

    fun bind(countryNewsTag: CountryNewsTag) {
        val moreTopAdapter = AdapterSelector.select(countryNewsTag, 5)
        txtTitle.text = countryNewsTag.newsTag
        recyclerView.run {
            setHasFixedSize(true)
            adapter = moreTopAdapter
            layoutManager =
        }
    }
}