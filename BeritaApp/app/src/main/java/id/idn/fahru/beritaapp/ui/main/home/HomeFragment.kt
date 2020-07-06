package id.idn.fahru.beritaapp.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import coil.size.Scale
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
    private lateinit var adapterMerged: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.resetData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = LayoutInflater.from(this.context)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        placeHolderAdapter = PlaceHolderAdapter()
        placeHolderAdapter.loadState = LoadingState.LOADING
        adapterRv = RvAdapter(homeViewModel, viewLifecycleOwner)
        adapterMerged = ConcatAdapter(placeHolderAdapter, adapterRv)
        binding.run {
            setHasOptionsMenu(true)
            homeRv.run {
                adapter = adapterMerged
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
            }
            logo.load(R.drawable.logo) {
                scale(Scale.FIT)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).run {
            setSupportActionBar(binding.homeToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.run {
            swipeContainer.apply {
                isRefreshing = true
                setOnRefreshListener {
                    placeHolderAdapter.loadState = LoadingState.LOADING
                    homeViewModel.resetData()
                }
                homeViewModel.run {
                    getListCountries.observe(viewLifecycleOwner, Observer {
                        adapterRv.addData(it)
                    })
                    loadingState.observe(viewLifecycleOwner, Observer {
                        placeHolderAdapter.loadState = it
                        isRefreshing = it == LoadingState.LOADING
                    })
                    errorMsg.observe(viewLifecycleOwner, Observer {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.action_refresh -> {
                placeHolderAdapter.loadState = LoadingState.LOADING
                homeViewModel.resetData()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
