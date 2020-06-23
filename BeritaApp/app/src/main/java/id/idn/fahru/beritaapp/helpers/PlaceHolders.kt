package id.idn.fahru.beritaapp.helpers

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

/**
 * Created by Imam Fahrur Rofi on 22/06/2020.
 */
object PlaceHolders {
    private val shimmer =
        Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setDuration(2000) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(0.6f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

    // This is the placeholder for the imageView
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
}