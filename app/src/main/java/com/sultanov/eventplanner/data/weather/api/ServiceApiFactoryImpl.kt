package com.sultanov.eventplanner.data.weather.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

internal class ServiceApiFactoryImpl @Inject constructor() : ServiceApiFactory {

    override fun create(): ServiceApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create()
    }

    private companion object {

        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}
