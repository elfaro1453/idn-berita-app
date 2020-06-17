package id.idn.fahru.beritaapp.ui.rvadapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.App
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.ItemHeadlineBinding
import id.idn.fahru.beritaapp.helpers.RecyclerViewTypes
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import id.idn.fahru.beritaapp.ui.detail.DetailActivity
import id.idn.fahru.beritaapp.ui.rvadapter.viewholder.ItemHeadlineVH
import kotlin.math.min

/**
 * Created by Imam Fahrur Rofi on 04/06/2020.
 */
class ItemHeadlineAdapter :
    RecyclerView.Adapter<ItemHeadlineVH>() {

    private var dataSize = 0
    private var rvTypes = RecyclerViewTypes.TOP_HEADLINE

    private val listData = mutableListOf<ArticlesItem>()
    fun addData(items: List<ArticlesItem>, size: Int, types: RecyclerViewTypes) {
        dataSize = size
        rvTypes = types
        listData.run {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHeadlineVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemHeadlineBinding = ItemHeadlineBinding.inflate(layoutInflater, parent, false)
        return ItemHeadlineVH(itemHeadlineBinding)
    }

    override fun getItemCount() = min(listData.size, dataSize)

    override fun onBindViewHolder(holder: ItemHeadlineVH, position: Int) {
        val articlesItem = listData[position]
        val imageParams = when (rvTypes) {
            RecyclerViewTypes.MAIN_TOP_HEADLINES ->
                ConstraintLayout.LayoutParams(
                    MATCH_PARENT,
                    App.context.resources.getDimensionPixelSize(
                        R.dimen.imageTop_max_height
                    )
                )
            else -> ConstraintLayout.LayoutParams(
                MATCH_PARENT,
                App.context.resources.getDimensionPixelSize(
                    R.dimen.imageSlide_max_height
                )
            )
        }
        val margin8dp = App.context.resources.getDimensionPixelSize(R.dimen.margin_8dp)
        val rootParams =
            if (rvTypes == RecyclerViewTypes.TOP_HEADLINE_HORIZONTAL) CoordinatorLayout.LayoutParams(
                App.context.resources.getDimensionPixelSize(R.dimen.containerSlide_max_width),
                WRAP_CONTENT
            ).apply {
                marginStart = margin8dp
                marginEnd = margin8dp
                bottomMargin = margin8dp
            } else null
        holder.bind(articlesItem, imageParams, rootParams)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.DETAIL_NEWS, articlesItem)
            }
            it.context.startActivity(intent)
        }
    }

    override fun getItemId(position: Int): Long {
        return listData[position].publishedAt!!.filter { it.isDigit() }.toLong()
    }
}