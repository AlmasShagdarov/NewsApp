package com.beone.newsapp.network

import com.beone.newsapp.BuildConfig
import com.beone.newsapp.network.topnews.NetworkTopNewsObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


private const val BASE_URL = BuildConfig.API_URL
private const val API_KEY = BuildConfig.API_KEY

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface NewsApiService {
    @Headers("X-Api-Key: $API_KEY")
    @GET("/v2/top-headlines")
    suspend fun getTopNews(@Query("country") country: String): NetworkTopNewsObject

    @Headers("X-Api-Key: $API_KEY")
    @GET("/v2/top-headlines")
    suspend fun getBusinessNews(@Query("country") country: String, @Query("category") category: String): NetworkTopNewsObject

}

object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}
