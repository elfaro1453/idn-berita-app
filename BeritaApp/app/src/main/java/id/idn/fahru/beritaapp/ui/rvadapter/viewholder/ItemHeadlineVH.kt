package id.idn.fahru.beritaapp.ui.rvadapter.viewholder

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import id.idn.fahru.beritaapp.databinding.ItemHeadlineBinding
import id.idn.fahru.beritaapp.helpers.DateTypes
import id.idn.fahru.beritaapp.helpers.PlaceHolders
import id.idn.fahru.beritaapp.helpers.crop
import id.idn.fahru.beritaapp.helpers.toDate
import id.idn.fahru.beritaapp.model.remote.ArticlesItem

/**
 * Created by Imam Fahrur Rofi on 04/06/2020.
 */
class ItemHeadlineVH(private val binding: ItemHeadlineBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private var maxtitle = 75

    fun bind(
        item: ArticlesItem,
        imageParams: ConstraintLayout.LayoutParams,
        rootParams: CoordinatorLayout.LayoutParams?
    ) {
        binding.run {
            rootParams?.let {
                root.layoutParams = it
                maxtitle = 40
            }
            textTitle.text = item.title?.crop(maxtitle)
            imageRvTopHeadline.apply {
                layoutParams = imageParams
                load(item.urlToImage) {
                    scale(Scale.FILL)
                    placeholder(PlaceHolders.shimmerDrawable)
                }
                contentDescription = item.description
            }
            item.publishedAt?.let {
                textDate.text = it.toDate(DateTypes.LocalizedDate)
            }
        }
    }
}