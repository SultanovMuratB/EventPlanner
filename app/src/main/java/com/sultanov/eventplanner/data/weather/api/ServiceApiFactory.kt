package com.sultanov.eventplanner.data.weather.api

internal interface ServiceApiFactory {

    fun create(): ServiceApi
}
