package id.idn.fahru.beritaapp.ui.rvadapter.viewholder

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import id.idn.fahru.beritaapp.databinding.ItemHeadlineBinding
import id.idn.fahru.beritaapp.helpers.DateTypes
import id.idn.fahru.beritaapp.helpers.crop
import id.idn.fahru.beritaapp.helpers.toDate
import id.idn.fahru.beritaapp.model.remote.ArticlesItem

/**
 * Created by Imam Fahrur Rofi on 04/06/2020.
 */
class ItemHeadline(private val binding: ItemHeadlineBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ArticlesItem, itemLayoutParams: ConstraintLayout.LayoutParams) {
        binding.run {
            textTitle.text = item.title?.crop(100)
            imageRvTopHeadline.apply {
                layoutParams = itemLayoutParams
                load(item.urlToImage) {
                    scale(Scale.FILL)
                }
                contentDescription = item.title
            }
            item.publishedAt?.let {
                textDate.text = it.toDate(DateTypes.LocalizedDate)
            }
        }
    }
}