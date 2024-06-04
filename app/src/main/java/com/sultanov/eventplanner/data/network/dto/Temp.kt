package com.sultanov.eventplanner.data.network.dto

import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("temp") val temp: String,
)
