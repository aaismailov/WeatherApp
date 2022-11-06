package com.example.weatherapp.data.network

import com.example.weatherapp.data.network.models.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/forecast.json?")
    suspend fun getForecast(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_CITY) q: String = "Baku",
        @Query(QUERY_PARAM_DAYS) days: Int = 7,
        @Query(QUERY_PARAM_AQI) aqi: String = "no",
        @Query(QUERY_PARAM_ALERTS) alerts: String = "no",
        @Query(QUERY_PARAM_LANG) lang: String = "ru"
    ): WeatherDto

    companion object {
        private const val API_KEY = "f8f1004ebb06472b9c0194519220411"
        private const val QUERY_PARAM_API_KEY = "key"
        private const val QUERY_PARAM_CITY = "q"
        private const val QUERY_PARAM_DAYS = "days"
        private const val QUERY_PARAM_AQI = "aqi"
        private const val QUERY_PARAM_ALERTS = "alerts"
        private const val QUERY_PARAM_LANG = "lang"
    }
}