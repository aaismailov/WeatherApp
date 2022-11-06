package com.example.weatherapp.domain

import androidx.lifecycle.LiveData

interface WeatherRepository {

    suspend fun loadData(city: String)

    suspend fun getCurrentWeather(): LiveData<WeatherEntity>

    suspend fun getWeatherByDays(): LiveData<List<WeatherEntity>>

    suspend fun getWeatherByHours(): LiveData<List<WeatherEntity>>
}