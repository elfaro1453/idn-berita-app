package id.idn.fahru.beritaapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.beritaapp.databinding.PhItemHeadlineMoreTopBinding
import id.idn.fahru.beritaapp.helpers.LoadingState

/**
 * Created by Imam Fahrur Rofi on 23/06/2020.
 */
class PlaceHolderAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var loadState: LoadingState = LoadingState.SUCCESS
        set(loadState) {
            if (field != loadState) {
                val displayOldItem = isLoading(field)
                val displayNewItem = isLoading(loadState)
                if (!displayOldItem && displayNewItem) {
                    notifyItemInserted(0)
                } else if (displayOldItem && !displayNewItem) {
                    notifyItemRemoved(0)
                }
                field = loadState
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PhItemHeadlineMoreTopBinding.inflate(layoutInflater, parent, false)
        return ItemHeadlineMoreTopPH(binding)
    }

    override fun getItemCount(): Int = if (isLoading(loadState)) 1 else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    private fun isLoading(loadState: LoadingState): Boolean {
        return loadState == LoadingState.LOADING
    }
}

class ItemHeadlineMoreTopPH(binding: PhItemHeadlineMoreTopBinding) :
    RecyclerView.ViewHolder(binding.root)