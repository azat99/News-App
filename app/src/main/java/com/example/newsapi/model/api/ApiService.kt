package com.example.newsapi.model.api

import com.example.newsapi.model.entity.NewsResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/"

interface ApiService {

    @GET("everything")
    fun getNewsList(
        @Query("q") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Deferred<NewsResponse>

    companion object {
        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

}