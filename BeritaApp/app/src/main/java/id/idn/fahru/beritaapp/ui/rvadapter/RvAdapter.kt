package id.idn.fahru.beritaapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.databinding.ItemHeadlineMoreTopBinding
import id.idn.fahru.beritaapp.databinding.ItemMoreTopBinding
import id.idn.fahru.beritaapp.model.local.CountryNewsTag
import id.idn.fahru.beritaapp.ui.main.home.HomeViewModel
import id.idn.fahru.beritaapp.ui.rvadapter.viewholder.ItemHeadlineMoreTopVH
import id.idn.fahru.beritaapp.ui.rvadapter.viewholder.ItemMoreTopVH

/**
 * Created by Imam Fahrur Rofi on 05/06/2020.
 */
class RvAdapter(private val viewModel: HomeViewModel, private val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private val listDataCountry = ArrayList<CountryNewsTag>()

    fun addData(items: List<CountryNewsTag>) {
        listDataCountry.run {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return listDataCountry[position].recyclerViewTypes.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        viewPool.setMaxRecycledViews(6, 10)
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemMoreTopBinding = ItemMoreTopBinding.inflate(layoutInflater, parent, false)
        val itemHeadLineMoreTopBinding =
            ItemHeadlineMoreTopBinding.inflate(layoutInflater, parent, false)
        return if (viewType == 0) {
            itemHeadLineMoreTopBinding.run {
                rvTop.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ItemHeadlineAdapter()
                }
                include.rvBottom.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ItemMainAdapter(5)
                }
            }
            ItemHeadlineMoreTopVH(itemHeadLineMoreTopBinding)
        } else {
            itemMoreTopBinding.run {
                rvBottom.run {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ItemMainAdapter(5)
                }
            }
            ItemMoreTopVH(itemMoreTopBinding)
        }
    }

    override fun getItemCount(): Int = listDataCountry.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        viewModel.run {
            fetchAPI(listDataCountry[position], position)
            getData(position).observe(lifecycleOwner, Observer {
                when (getItemViewType(position)) {
                    0 -> {
                        (holder as ItemHeadlineMoreTopVH).bind(it, position)
                    }
                    else -> {
                        (holder as ItemMoreTopVH).bind(it, position)
                    }
                }
            })
        }
    }
}