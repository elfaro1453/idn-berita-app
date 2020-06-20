package id.idn.fahru.beritaapp.ui.rvadapter.viewholder

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.ItemMoreTopBinding
import id.idn.fahru.beritaapp.model.local.CountryNewsTag
import id.idn.fahru.beritaapp.ui.rvadapter.ItemHeadlineAdapter
import id.idn.fahru.beritaapp.ui.rvadapter.ItemMainAdapter
import id.idn.fahru.beritaapp.ui.rvadapter.helper.AdapterSelector
import id.idn.fahru.beritaapp.ui.rvadapter.helper.LayoutManagerSelector
import java.util.*

/**
 * Created by Imam Fahrur Rofi on 05/06/2020.
 */
class ItemMoreTopVH(
    binding: ItemMoreTopBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    private val viewPool = RecyclerView.RecycledViewPool()
    private val btnMore = binding.btnLoadMore
    private val txtTitle = binding.btnLoadMore.textTitle
    private val recyclerView = binding.rvBottom

    fun bind(countryNewsTag: CountryNewsTag, position: Int) {
        val moreTopAdapter = AdapterSelector.select(countryNewsTag, 5)
        moreTopAdapter.setHasStableIds(true)
        val rvLayoutManager = LayoutManagerSelector.select(countryNewsTag, recyclerView.context)
        txtTitle.text = countryNewsTag.newsTag.toUpperCase(Locale.getDefault())
        btnMore.run {
            root.visibility = View.VISIBLE
            buttonMore.setOnClickListener {
                val bundle = bundleOf("position" to position)
                it.findNavController()
                    .navigate(R.id.action_navigation_home_to_listNewsFragment, bundle)
            }
        }
        recyclerView.run {
            layoutManager = rvLayoutManager
            viewPool.getRecycledView(countryNewsTag.recyclerViewTypes.ordinal)
            setHasFixedSize(true)
            adapter = moreTopAdapter
            when (moreTopAdapter) {
                is ItemHeadlineAdapter -> moreTopAdapter.addData(
                    countryNewsTag.listNews,
                    5,
                    countryNewsTag.recyclerViewTypes
                )
                else -> (moreTopAdapter as ItemMainAdapter).addData(countryNewsTag.listNews.take(5))
            }
            visibility = View.VISIBLE
        }
    }
}