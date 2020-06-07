package id.idn.fahru.beritaapp.ui.main.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.idn.fahru.beritaapp.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {

    private lateinit var bookmarkViewModel: BookmarkViewModel
    private lateinit var fragmentBookmarkBinding: FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookmarkViewModel =
            ViewModelProvider(this).get(BookmarkViewModel::class.java)
        fragmentBookmarkBinding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)

        bookmarkViewModel.textBookmark.observe(viewLifecycleOwner, Observer {
            fragmentBookmarkBinding.textBookmark.text = it
        })

        fragmentBookmarkBinding.buttonBookmark.setOnClickListener {

        }
        return fragmentBookmarkBinding.root
    }
}