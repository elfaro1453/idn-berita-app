package id.idn.fahru.beritaapp.api.service

import id.idn.fahru.beritaapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        requestBuilder.addHeader("Authorization", BuildConfig.API_KEY)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}