package id.idn.fahru.beritaapp.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.api.localsource.DataFactory
import id.idn.fahru.beritaapp.databinding.FragmentHomeBinding
import id.idn.fahru.beritaapp.helpers.LoadingState
import id.idn.fahru.beritaapp.ui.rvadapter.RvAdapter

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterRv: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).run {
            setSupportActionBar(binding.homeToolbar)
            supportActionBar?.title = resources.getString(R.string.app_name)
        }
        adapterRv = RvAdapter(homeViewModel, viewLifecycleOwner)
        binding.homeRv.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRv
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataCountries = DataFactory.dataSource()
        adapterRv.addData(dataCountries)
        binding.run {
            swipeContainer.apply {
                isRefreshing = true
                setOnRefreshListener {
                    homeViewModel.resetData()
                    adapterRv.addData(dataCountries)
                }
                homeViewModel.loadingState().observe(viewLifecycleOwner, Observer {
                    isRefreshing = it == LoadingState.LOADING
                })
                homeViewModel.errorMsg().observe(viewLifecycleOwner, Observer {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}