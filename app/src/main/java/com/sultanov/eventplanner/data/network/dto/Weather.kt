package com.sultanov.eventplanner.data.network.dto

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("icon") val icon: String,
)
