package com.picpay.desafio.android.core.remote.service

import com.picpay.desafio.android.core.remote.api.ContactApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MyService {

    private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
    private const val maxSecondsToRequest: Long = 30

    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .readTimeout(maxSecondsToRequest, TimeUnit.SECONDS)
            .connectTimeout(maxSecondsToRequest, TimeUnit.SECONDS)
            .writeTimeout(maxSecondsToRequest, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getService(): ContactApi = retrofit.create(ContactApi::class.java)
}