package id.idn.fahru.beritaapp.ui.detailnews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.idn.fahru.beritaapp.model.remote.ArticlesItem
import id.idn.fahru.beritaapp.room.NewsDB
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Imam Fahrur Rofi on 07/07/2020.
 */

class DetailActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val newsDao = NewsDB.getDatabase(application).newsDao()

    fun isArticleSaved(url: String): LiveData<Int> = newsDao.checkIsNewsSaved(url)

    fun deleteArticle(articlesItem: ArticlesItem) {
        viewModelScope.launch {
            articlesItem.url?.let { newsDao.delete(it) }
            Timber.e("Article Deleted from DB")
        }
    }

    fun insertArticle(articlesItem: ArticlesItem) {
        viewModelScope.launch {
            newsDao.insert(articlesItem)
            Timber.e("Article Inserted to DB")
        }
    }
}