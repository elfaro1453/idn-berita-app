package id.idn.fahru.beritaapp.ui.main.listnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.idn.fahru.beritaapp.App
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.ListNewsFragmentBinding
import id.idn.fahru.beritaapp.helpers.LoadingState
import id.idn.fahru.beritaapp.ui.main.home.HomeViewModel
import id.idn.fahru.beritaapp.ui.rvadapter.ItemMainAdapter
import java.util.*

class ListNewsFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: ListNewsFragmentBinding
    private lateinit var adapterRv: ItemMainAdapter
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
        binding.run {
            homeViewModel.getCountryNewsTag(dataPosition)
                .observe(requireActivity(), Observer { countryNewsTag ->
                    adapterRv.addData(countryNewsTag.listNews)
                    (activity as AppCompatActivity).run {
                        setSupportActionBar(binding.homeToolbar)
                        supportActionBar?.title =
                            (if (countryNewsTag.newsTag.isEmpty()) App.context.resources.getText(
                                R.string.text_headlines
                            ) as String else countryNewsTag.newsTag).toUpperCase(Locale.getDefault())
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    }
                    swipeContainer.apply {
                        isRefreshing = true
                        setOnRefreshListener {
                            homeViewModel.fetchAPI(dataPosition, countryNewsTag)
                        }
                        homeViewModel.loadingState.observe(viewLifecycleOwner, Observer {
                            isRefreshing = it == LoadingState.LOADING
                        })
                    }
                })
        }
    }
}