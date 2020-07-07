package id.idn.fahru.beritaapp.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import id.idn.fahru.beritaapp.R
import id.idn.fahru.beritaapp.databinding.BookmarkFragmentBinding

class AccountFragment : Fragment() {
    private val viewModel: AccountViewModel by viewModels()
    private lateinit var binding: BookmarkFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.account_fragment, container, false)
    }

}