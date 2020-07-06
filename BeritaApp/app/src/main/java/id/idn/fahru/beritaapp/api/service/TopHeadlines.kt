package id.idn.fahru.beritaapp.api.service

import id.idn.fahru.beritaapp.model.remote.ResponseNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Imam Fahrur Rofi on 29/05/2020.
 */
interface TopHeadlines {
    @GET("/v2/top-headlines")
    suspend fun fetchHeadlines(
        @Query("country") country: String,
        @Query("category") category: String
    ): Response<ResponseNews>
}