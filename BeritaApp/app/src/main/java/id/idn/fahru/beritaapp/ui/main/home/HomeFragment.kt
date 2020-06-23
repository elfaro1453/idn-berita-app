package id.idn.fahru.beritaapp.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.FragmentHomeBinding
import id.idn.fahru.beritaapp.helpers.LoadingState
import id.idn.fahru.beritaapp.ui.rvadapter.PlaceHolderAdapter
import id.idn.fahru.beritaapp.ui.rvadapter.RvAdapter

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterRv: RvAdapter
    private lateinit var placeHolderAdapter: PlaceHolderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapterRv = RvAdapter(homeViewModel, requireActivity())
        placeHolderAdapter = PlaceHolderAdapter()
        placeHolderAdapter.loadState = LoadingState.LOADING
        val layoutInflater = LayoutInflater.from(this.context)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.homeRv.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MergeAdapter(placeHolderAdapter, adapterRv)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).run {
            setSupportActionBar(binding.homeToolbar)
            supportActionBar?.title = resources.getString(R.string.app_name)
        }
        binding.run {
            swipeContainer.apply {
                isRefreshing = true
                setOnRefreshListener {
                    placeHolderAdapter.loadState = LoadingState.LOADING
                    homeViewModel.resetData()
                }
                homeViewModel.run {
                    getListCountries().observe(requireActivity(), Observer {
                        adapterRv.addData(it)
                    })
                    loadingState().observe(requireActivity(), Observer {
                        placeHolderAdapter.loadState = it
                        isRefreshing = it == LoadingState.LOADING
                    })
                    errorMsg().observe(requireActivity(), Observer {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }
    }
}