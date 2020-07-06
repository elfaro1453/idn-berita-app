package id.idn.fahru.beritaapp.helpers

import kotlin.random.Random

/**
 * Created by Imam Fahrur Rofi on 04/06/2020.
 */

fun <T : Enum<*>> Class<T>.randomEnum(): T {
    return if (this.enumConstants.isNullOrEmpty()) error("ENUM EMPTY") else {
        val i: Int = Random.nextInt(1, this.enumConstants!!.size)
        this.enumConstants!![i]
    }
}

enum class RecyclerViewTypes {
    MAIN_TOP_HEADLINES, RV_MAIN, TOP_HEADLINE, TOP_HEADLINE_HORIZONTAL
}

enum class LoadingState {
    LOADING, SUCCESS, ERROR
}