package id.idn.fahru.beritaapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.App
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.ItemRvTopHeadlineBinding
import id.idn.fahru.beritaapp.helpers.RecyclerViewTypes
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import id.idn.fahru.beritaapp.ui.rvadapter.viewholder.ItemRvTopHeadlineViewHolder
import kotlin.math.min

/**
 * Created by Imam Fahrur Rofi on 04/06/2020.
 */
class ItemRvTopHeadlineAdapter(private val size: Int, private val rvTypes : RecyclerViewTypes) :
    RecyclerView.Adapter<ItemRvTopHeadlineViewHolder>() {
    private val listData = mutableListOf<ArticlesItem>()

    fun addData(items: List<ArticlesItem>) {
        listData.run {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRvTopHeadlineViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemRvMainBinding = ItemRvTopHeadlineBinding.inflate(layoutInflater, parent, false)
        return ItemRvTopHeadlineViewHolder(itemRvMainBinding)
    }

    override fun getItemCount() = min(listData.size, size)

    override fun onBindViewHolder(holder: ItemRvTopHeadlineViewHolder, position: Int) {
        val layoutParams = when (rvTypes) {
            RecyclerViewTypes.MAIN_TOP_HEADLINES ->
                ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    App.context.resources.getDimensionPixelSize(
                        R.dimen.imageTop_max_height
                    )
                )
            else -> ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                App.context.resources.getDimensionPixelSize(
                    R.dimen.imageSlide_max_height
                )
            )
        }
        holder.bind(listData.get(position), layoutParams)
    }
}