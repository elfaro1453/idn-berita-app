package id.idn.fahru.beritaapp.api.localsource

import id.idn.fahru.beritaapp.helpers.RecyclerViewTypes
import id.idn.fahru.beritaapp.helpers.randomEnum
import id.idn.fahru.beritaapp.model.local.CountryNewsTag

/**
 * Created by Imam Fahrur Rofi on 04/06/2020.
 */
object DataFactory {
    private val category = arrayListOf(
        "",
        "technology",
        "business",
        "entertainment",
        "health",
        "science",
        "sports"
    )

    fun dataSource(): ArrayList<CountryNewsTag> {
        val list = arrayListOf<CountryNewsTag>()
        category.forEachIndexed { position, it ->
            list.add(
                CountryNewsTag(
                    "id", it,
                    if (position == 0) RecyclerViewTypes.MAIN_TOP_HEADLINES else RecyclerViewTypes::class.java.randomEnum(),
                    ArrayList()
                )
            )
        }
        return list
    }
}