package com.sultanov.eventplanner.data.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiFactory {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val KEY_PARAM = "appid"
    private const val KEY = "2642a7374e493896a3729c45238207cb"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val oldRequest = chain.request()
            val newUrl = oldRequest
                .url()
                .newBuilder()
                .addQueryParameter(KEY_PARAM, KEY)
                .build()
            val newRequest = oldRequest.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create()
}