package com.beone.newsapp.network

import android.annotation.SuppressLint
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*


private const val BASE_URL = "https://newsapi.org"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlinesAsync(@Query("country") country: String,
         @Query("apiKey") apiKey: String): NewsObject
        //@Query("country") country: String,
       // @Query("page") newsCount: Int

}

object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}

object UnsafeOkHttpClient {
    // Create a trust manager that does not validate certificate chains
    val unsafeOkHttpClient:
    // Install the all-trusting trust manager
    // Create an ssl socket factory with our all-trusting manager
            OkHttpClient
        get() = try { // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }

                }
            )
            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
}