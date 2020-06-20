package id.idn.fahru.beritaapp.ui.main.listnews

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
import id.idn.fahru.beritaapp.App
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.ListNewsFragmentBinding
import id.idn.fahru.beritaapp.helpers.LoadingState
import id.idn.fahru.beritaapp.model.local.CountryNewsTag
import id.idn.fahru.beritaapp.ui.main.home.HomeViewModel
import id.idn.fahru.beritaapp.ui.rvadapter.ItemMainAdapter
import java.util.*

class ListNewsFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: ListNewsFragmentBinding
    private lateinit var adapterRv: ItemMainAdapter
    private lateinit var countryNewsTag: CountryNewsTag
    private var dataPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = LayoutInflater.from(this.context)
        dataPosition = arguments?.getInt("position") as Int
        adapterRv = ItemMainAdapter(Int.MAX_VALUE)
        binding = ListNewsFragmentBinding.inflate(layoutInflater, container, false)
        binding.listNewsRv.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRv
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getData(dataPosition).observe(requireActivity(), Observer {
            adapterRv.addData(it.listNews)
            countryNewsTag = it
            (activity as AppCompatActivity).run {
                setSupportActionBar(binding.homeToolbar)
                supportActionBar?.title =
                    (if (countryNewsTag.newsTag.isEmpty()) App.context.resources.getText(
                        R.string.text_headlines
                    ) as String else countryNewsTag.newsTag).toUpperCase(Locale.getDefault())
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        })
        binding.run {
            swipeContainer.apply {
                isRefreshing = true
                setOnRefreshListener {
                    homeViewModel.fetchAPI(countryNewsTag, dataPosition)
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