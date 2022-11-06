package com.example.weatherapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ConditionDto(
    @SerializedName("text")
    @Expose
    val conditionText: String,

    @SerializedName("icon")
    @Expose
    val conditionIcon: String
)