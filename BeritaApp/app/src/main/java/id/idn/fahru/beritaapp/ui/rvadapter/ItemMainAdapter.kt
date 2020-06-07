package id.idn.fahru.beritaapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.databinding.ItemMainBinding
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import id.idn.fahru.beritaapp.ui.rvadapter.viewholder.ItemMainVH
import kotlin.math.min

/**
 * Created by Imam Fahrur Rofi on 02/06/2020.
 */
class ItemMainAdapter(private val size: Int) : RecyclerView.Adapter<ItemMainVH>() {
    private val listData = mutableListOf<ArticlesItem>()

    fun addData(items: List<ArticlesItem>) {
        listData.clear()
        listData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMainVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemMainBinding = ItemMainBinding.inflate(layoutInflater, parent, false)
        return ItemMainVH(itemMainBinding)
    }

    override fun getItemCount() = min(listData.size, size)

    override fun onBindViewHolder(holder: ItemMainVH, position: Int) {
        holder.bind(listData.get(position))
    }

    override fun getItemId(position: Int): Long {
        return listData[position].publishedAt!!.filter { it.isDigit() }.toLong()
    }
}