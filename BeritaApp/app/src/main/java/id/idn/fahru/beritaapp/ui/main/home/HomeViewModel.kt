package id.idn.fahru.beritaapp.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.idn.fahru.beritaapp.api.localsource.DataFactory
import id.idn.fahru.beritaapp.api.service.ServiceBuilder
import id.idn.fahru.beritaapp.api.service.TopHeadlines
import id.idn.fahru.beritaapp.helpers.LoadingState
import id.idn.fahru.beritaapp.model.local.CountryNewsTag
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.collections.set

class HomeViewModel : ViewModel() {
    private val topHeadlines = ServiceBuilder.buildService(TopHeadlines::class.java)
    private val mapData = mutableMapOf<Int, MutableLiveData<CountryNewsTag>>()
    private val listCountryNewsTag = MutableLiveData<List<CountryNewsTag>>()
    private val _loadState = MutableLiveData<LoadingState>()
    private val _errorMsg = MutableLiveData<String>()

    val loadingState: LiveData<LoadingState> = _loadState
    val errorMsg: LiveData<String> = _errorMsg
    val getListCountries: LiveData<List<CountryNewsTag>> = listCountryNewsTag

    fun fetchAPI(position: Int, countryNewsTag: CountryNewsTag) {
        mapData[position] = MutableLiveData()
        viewModelScope.launch {
            try {
                val call =
                    topHeadlines.fetchHeadlines(countryNewsTag.country, countryNewsTag.newsTag)
                if (call.isSuccessful) {
                    call.body()?.articles?.let {
                        countryNewsTag.listNews = it
                        mapData[position]?.postValue(countryNewsTag)
                        _loadState.postValue(LoadingState.SUCCESS)
                    }
                } else {
                    val errorMsg = call.message()
                    Timber.e(errorMsg)
                    _loadState.postValue(LoadingState.ERROR)
                    _errorMsg.postValue("error: $errorMsg")
                }
            } catch (error: Exception) {
                Timber.e(error)
            }
        }
    }

    fun resetData() {
        Timber.e("Reset Data Called")
        mapData.clear()
        val dataCountries = DataFactory.dataSource()
        listCountryNewsTag.postValue(dataCountries)
    }

    fun getCountryNewsTag(position: Int): LiveData<CountryNewsTag> =
        mapData[position]!!
}