package id.idn.fahru.beritaapp.ui.rvadapter.viewholder

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.databinding.ItemHeadlineBinding
import id.idn.fahru.beritaapp.databinding.ItemHeadlineMoreTopBinding
import id.idn.fahru.beritaapp.model.local.CountryNewsTag
import id.idn.fahru.beritaapp.ui.main.home.HomeViewModel

/**
 * Created by Imam Fahrur Rofi on 05/06/2020.
 */
class ItemHeadlineMoreTop(private val binding: ItemHeadlineMoreTopBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var homeViewModel: HomeViewModel
    private val rvTop = binding.rvTop
    private val rvBottom = binding.include.rvBottom

    fun bind(countryNewsTag: CountryNewsTag) {
        rvTop.run{
            setHasFixedSize(true)
            layoutManager =
        }
    }
}