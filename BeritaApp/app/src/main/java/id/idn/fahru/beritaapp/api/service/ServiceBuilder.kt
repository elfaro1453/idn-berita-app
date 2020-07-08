package id.idn.fahru.beritaapp.api.service

import id.idn.fahru.beritaapp.BuildConfig
import id.idn.fahru.beritaapp.model.remote.ResponseNews
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Imam Fahrur Rofi on 29/05/2020.
 */
object ServiceBuilder {
    private val client = OkHttpClient.Builder().addInterceptor(RequestInterceptor()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        requestBuilder.addHeader("Authorization", BuildConfig.API_KEY)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

interface TopHeadlines {
    @GET("/v2/top-headlines")
    suspend fun fetchHeadlines(
        @Query("country") country: String,
        @Query("category") category: String
    ): retrofit2.Response<ResponseNews>
}