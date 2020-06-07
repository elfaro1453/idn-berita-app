package id.idn.fahru.beritaapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.databinding.ItemRvMainBinding
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import id.idn.fahru.beritaapp.ui.rvadapter.viewholder.ItemRvMainViewHolder
import kotlin.math.min

/**
 * Created by Imam Fahrur Rofi on 02/06/2020.
 */
class ItemRvMainAdapter(private val size: Int) : RecyclerView.Adapter<ItemRvMainViewHolder>() {
    private val listData = ArrayList<ArticlesItem>()

    fun addData(items: List<ArticlesItem>) {
        listData.clear()
        listData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRvMainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemRvMainBinding = ItemRvMainBinding.inflate(layoutInflater, parent, false)
        return ItemRvMainViewHolder(itemRvMainBinding)
    }

    override fun getItemCount() = min(listData.size, size)

    override fun onBindViewHolder(holder: ItemRvMainViewHolder, position: Int) {
        holder.bind(listData.get(position))
    }
}