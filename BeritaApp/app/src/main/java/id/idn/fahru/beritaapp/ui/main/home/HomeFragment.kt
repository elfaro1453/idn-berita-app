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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import coil.size.Scale
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.HomeFragmentBinding
import id.idn.fahru.beritaapp.helpers.LoadingState
import id.idn.fahru.beritaapp.ui.rvadapter.PlaceHolderAdapter
import id.idn.fahru.beritaapp.ui.rvadapter.RvAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var adapterRv: RvAdapter
    private lateinit var placeHolderAdapter: PlaceHolderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) homeViewModel.resetData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        placeHolderAdapter = PlaceHolderAdapter()
        adapterRv = RvAdapter(homeViewModel, viewLifecycleOwner)
        binding.run {
            setHasOptionsMenu(true)
            rvPlaceholder.run {
                adapter = placeHolderAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
            }
            homeRv.run {
                adapter = adapterRv
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
        binding.swipeContainer.apply {
            isRefreshing = true
            itemLoading()
            setOnRefreshListener {
                itemLoading()
                homeViewModel.resetData()
            }
        }
        homeViewModel.run {
            getListCountries.observe(viewLifecycleOwner, Observer {
                adapterRv.addData(it)
            })
            loadingState.observe(viewLifecycleOwner, Observer {
                binding.swipeContainer.isRefreshing = it == LoadingState.LOADING
                if (it == LoadingState.SUCCESS)
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(1000)
                        itemLoaded()
                    }
            })
            errorMsg.observe(viewLifecycleOwner, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
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
                itemLoading()
                homeViewModel.resetData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun itemLoading() {
        binding.homeRv.visibility = View.INVISIBLE
        binding.rvPlaceholder.visibility = View.VISIBLE
    }

    private fun itemLoaded() {
        binding.homeRv.visibility = View.VISIBLE
        binding.rvPlaceholder.visibility = View.GONE
    }
}
