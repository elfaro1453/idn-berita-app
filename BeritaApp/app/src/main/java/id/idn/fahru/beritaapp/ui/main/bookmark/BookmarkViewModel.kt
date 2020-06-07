package id.idn.fahru.beritaapp.ui.main.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookmarkViewModel : ViewModel() {
    private val _textBookmark = MutableLiveData<String>().apply {
        value = "This is Bookmark Fragment"
    }
    val textBookmark: LiveData<String> = _textBookmark
}