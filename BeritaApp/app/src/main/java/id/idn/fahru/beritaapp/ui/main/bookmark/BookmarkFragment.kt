package id.idn.fahru.beritaapp.ui.main.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.BookmarkFragmentBinding
import id.idn.fahru.beritaapp.ui.rvadapter.ItemMainAdapter

class BookmarkFragment : Fragment() {

    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()
    private lateinit var binding: BookmarkFragmentBinding
    private lateinit var itemMainAdapter: ItemMainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BookmarkFragmentBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).run {
            setSupportActionBar(binding.homeToolbar)
            supportActionBar?.title = this.resources.getString(R.string.title_bookmark)
        }
        itemMainAdapter = ItemMainAdapter(Int.MAX_VALUE)
        binding.run {
            setHasOptionsMenu(true)
            rvBookmark.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = itemMainAdapter
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookmarkViewModel.bookmarkedArticle.observe(viewLifecycleOwner, Observer {
            binding.txtWarning.run {
                if (it.isNullOrEmpty()) visibility = View.VISIBLE else visibility = View.GONE
            }
            itemMainAdapter.addData(it)
        })
    }
}