package id.idn.fahru.beritaapp.ui.main.bookmark

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import id.idn.fahru.beritaapp.room.NewsDB
import kotlinx.coroutines.launch

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
    private val newsDao = NewsDB.getDatabase(application).newsDao()
    private val _bookmarked = newsDao.getAllSavedNews()

    private val _textBookmark = MutableLiveData<String>().apply {
        value = "This is Bookmark Fragment"
    }
    val textBookmark: LiveData<String> = _textBookmark

    val bookmarkedArticle: LiveData<List<ArticlesItem>>
        get() = _bookmarked

    fun insertArticle(articlesItem: ArticlesItem) {
        viewModelScope.launch {
            newsDao.insert(articlesItem)
        }
    }

    fun deleteArticle(articlesItem: ArticlesItem) {
        viewModelScope.launch {
            articlesItem.url?.let { newsDao.delete(it) }
        }
    }

    fun deleteAllArticle() {
        viewModelScope.launch {
            newsDao.deleteAll()
        }
    }
}