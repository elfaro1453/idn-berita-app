package id.idn.fahru.beritaapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _textDetail = MutableLiveData<String>().apply {
        value = "This is Detail Fragment"
    }
    private val _textBookmark = MutableLiveData<String>().apply {
        value = "This is Bookmark Fragment"
    }
    private val _textHome = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val textHome: LiveData<String> = _textHome
    val textBookmark: LiveData<String> = _textBookmark
    val textDetail: LiveData<String> = _textDetail
}