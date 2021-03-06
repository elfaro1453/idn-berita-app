package id.idn.fahru.beritaapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.databinding.PhItemHeadlineMoreTopBinding

/**
 * Created by Imam Fahrur Rofi on 23/06/2020.
 */
class PlaceHolderAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PhItemHeadlineMoreTopBinding.inflate(layoutInflater, parent, false)
        return ItemHeadlineMoreTopPH(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    internal class ItemHeadlineMoreTopPH(binding: PhItemHeadlineMoreTopBinding) :
        RecyclerView.ViewHolder(binding.root)
}