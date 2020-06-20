package id.idn.fahru.beritaapp.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.idn.fahru.beritaapp.api.localsource.DataFactory
import id.idn.fahru.beritaapp.api.service.ServiceBuilder
import id.idn.fahru.beritaapp.api.service.TopHeadlines
import id.idn.fahru.beritaapp.helpers.LoadingState
import id.idn.fahru.beritaapp.model.local.CountryNewsTag
import id.idn.fahru.beritaapp.model.remote.ResponseNews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class HomeViewModel : ViewModel() {
    private val topHeadlines = ServiceBuilder.buildService(TopHeadlines::class.java)
    private val mapData = mutableMapOf<Int, MutableLiveData<CountryNewsTag>>()
    private val listCountryNewsTag = MutableLiveData<List<CountryNewsTag>>()
    private val _loadState = MutableLiveData<LoadingState>()
    private val _errorMsg = MutableLiveData<String>()

    init {
        resetData()
    }

    fun fetchAPI(countryNewsTag: CountryNewsTag, position: Int) {
        mapData[position] = MutableLiveData()
        _loadState.value = LoadingState.LOADING
        val call = topHeadlines.fetchHeadlines(countryNewsTag.country, countryNewsTag.newsTag)
        call.enqueue(
            object : Callback<ResponseNews> {
                override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                    Timber.e(t)
                    _loadState.postValue(LoadingState.ERROR)
                    _errorMsg.postValue("error: $t")
                }

                override fun onResponse(
                    call: Call<ResponseNews>,
                    response: Response<ResponseNews>
                ) {
                    response.body()?.run {
                        articles?.let {
                            countryNewsTag.listNews = it
                            mapData[position]?.postValue(countryNewsTag)
                            _loadState.postValue(LoadingState.SUCCESS)
                        }
                    }
                }
            }
        )
    }

    fun loadingState(): LiveData<LoadingState> = _loadState
    fun errorMsg(): LiveData<String> = _errorMsg

    fun resetData() {
        mapData.clear()
        val dataCountries = DataFactory.dataSource()
        listCountryNewsTag.postValue(dataCountries)
    }

    fun getListCountries(): LiveData<List<CountryNewsTag>> = listCountryNewsTag

    fun getData(position: Int): LiveData<CountryNewsTag> =
        mapData[position] as LiveData<CountryNewsTag>
}