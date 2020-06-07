package id.idn.fahru.beritaapp.ui.rvadapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.App
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.ItemHeadlineMoreTopBinding
import id.idn.fahru.beritaapp.model.local.CountryNewsTag
import id.idn.fahru.beritaapp.ui.rvadapter.ItemHeadlineAdapter
import id.idn.fahru.beritaapp.ui.rvadapter.ItemMainAdapter
import id.idn.fahru.beritaapp.ui.rvadapter.helper.AdapterSelector
import id.idn.fahru.beritaapp.ui.rvadapter.helper.LayoutManagerSelector
import java.util.*

/**
 * Created by Imam Fahrur Rofi on 05/06/2020.
 */
class ItemHeadlineMoreTopVH(private val binding: ItemHeadlineMoreTopBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val viewPool = RecyclerView.RecycledViewPool()
    private val rvTop = binding.rvTop
    private val rvBottom = binding.include.rvBottom
    private val btnMore = binding.include.btnLoadMore
    private val txtTitle = binding.include.btnLoadMore.textTitle

    fun bind(countryNewsTag: CountryNewsTag) {
        val rvTopAdapter = AdapterSelector.select(countryNewsTag, 1)
        val rvBottomAdapter = AdapterSelector.select(countryNewsTag, 5)
        val rvTopLayoutManager = LayoutManagerSelector.select(countryNewsTag, rvTop.context)
        val rvBottomLayoutManager = LayoutManagerSelector.select(countryNewsTag, rvTop.context)
        rvTop.run {
            layoutManager = rvTopLayoutManager
            setHasFixedSize(true)
            adapter = rvTopAdapter
            viewPool.getRecycledView(countryNewsTag.recyclerViewTypes.ordinal)

            if (rvTopAdapter is ItemHeadlineAdapter) {
                rvTopAdapter.addData(
                    countryNewsTag.listNews.take(1),
                    1,
                    countryNewsTag.recyclerViewTypes
                )
            } else {
                (rvTopAdapter as ItemMainAdapter).addData(countryNewsTag.listNews.take(1))
            }
        }
        rvBottom.run {
            layoutManager = rvBottomLayoutManager
            adapter = rvBottomAdapter
            setHasFixedSize(true)
            viewPool.getRecycledView(countryNewsTag.recyclerViewTypes.ordinal)
            if (rvBottomAdapter is ItemHeadlineAdapter) {
                rvBottomAdapter.addData(
                    countryNewsTag.listNews.drop(1).take(5),
                    5,
                    countryNewsTag.recyclerViewTypes
                )
            } else {
                (rvBottomAdapter as ItemMainAdapter).addData(
                    countryNewsTag.listNews.drop(1).take(5)
                )
            }
            visibility = View.VISIBLE
        }
        txtTitle.text =
            (if (countryNewsTag.newsTag == "") countryNewsTag.newsTag else App.context.resources.getText(
                R.string.text_headlines
            ) as String).toUpperCase(Locale.getDefault())
        btnMore.root.visibility = View.VISIBLE
    }
}