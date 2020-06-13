package id.idn.fahru.newsapp.service

import id.idn.fahru.newsapp.model.ResponseNews
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

object RetrofitBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun getService() = retrofit.create(TopHeadlines::class.java)
}

interface TopHeadlines {
    // Replace YOUR_API_KEY with your api key from https://newsapi.org
    @Headers("Authorization: YOUR_API_KEY")
    @GET("/v2/top-headlines?country=id")
    fun fetchHeadlines(): Call<ResponseNews>
}