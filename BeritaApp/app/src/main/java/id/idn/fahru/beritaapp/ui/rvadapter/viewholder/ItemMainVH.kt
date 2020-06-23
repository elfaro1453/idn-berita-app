package id.idn.fahru.beritaapp.ui.rvadapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import id.idn.fahru.beritaapp.databinding.ItemMainBinding
import id.idn.fahru.beritaapp.helpers.DateTypes
import id.idn.fahru.beritaapp.helpers.PlaceHolders
import id.idn.fahru.beritaapp.helpers.crop
import id.idn.fahru.beritaapp.helpers.toDate
import id.idn.fahru.beritaapp.model.remote.ArticlesItem

/**
 * Created by Imam Fahrur Rofi on 01/06/2020.
 */
class ItemMainVH(private val binding: ItemMainBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ArticlesItem) {
        binding.run {
            textTitle.text = item.title?.crop(100)
            imageRvMain.apply {
                load(item.urlToImage) {
                    scale(Scale.FILL)
                    placeholder(PlaceHolders.shimmerDrawable)
                }
                contentDescription = item.description
            }
            item.publishedAt?.let {
                textDate.text = it.toDate(DateTypes.RelativeDate)
            }
        }
    }
}