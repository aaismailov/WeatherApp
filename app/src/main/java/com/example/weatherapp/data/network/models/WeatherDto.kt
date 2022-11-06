package com.example.weatherapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("location")
    @Expose
    val location: LocationDto,

    @SerializedName("current")
    @Expose
    val current: CurrentWeatherDto,

    @SerializedName("forecast")
    @Expose
    val forecast: ForecastDaysDto
)


data class LocationDto (
    @SerializedName("name")
    @Expose
    val city: String
)


data class CurrentWeatherDto (
    @SerializedName("last_updated")
    @Expose
    val lastUpdated: String,

    @SerializedName("temp_c")
    @Expose
    val tempC: Double,

    @SerializedName("condition")
    @Expose
    val condition: ConditionDto
)


data class ForecastDaysDto (
    @SerializedName("forecastday")
    @Expose
    val forecastForDaysList: List<ForecastDayDto>
)