package com.example.weatherapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastDayDto (
    @SerializedName("date_epoch")
    @Expose
    val idDay: Int,

    @SerializedName("date")
    @Expose
    val date: String,

    @SerializedName("day")
    @Expose
    val day: DayDto,

    @SerializedName("hour")
    @Expose
    val forecastForHoursList: List<HourDto>
)

data class DayDto(
    @SerializedName("maxtemp_c")
    @Expose
    val maxTemp: Double,

    @SerializedName("mintemp_c")
    @Expose
    val minTemp: Double,

    @SerializedName("avgtemp_c")
    @Expose
    val averageTemp: Double,

    @SerializedName("condition")
    @Expose
    val condition: ConditionDto
)


data class HourDto (
    @SerializedName("time_epoch")
    @Expose
    val idHour: Int,

    @SerializedName("time")
    @Expose
    val time: String,

    @SerializedName("temp_c")
    @Expose
    val tempC: Double,

    @SerializedName("condition")
    @Expose
    val condition: ConditionDto
)