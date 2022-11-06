package com.example.weatherapp.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.WeatherEntity
import com.example.weatherapp.utils.MutableResultFlow
import com.example.weatherapp.utils.loadOrError
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = WeatherRepositoryImpl(application)

    var currentWeather  = MutableResultFlow<WeatherEntity>()
    var hoursList  = MutableResultFlow<List<WeatherEntity>>()
    var daysList  = MutableResultFlow<List<WeatherEntity>>()

    val cityPrefs: StateFlow<String> = repository.cityPrefs

    fun loadData(
        city: String = cityPrefs.value // By default, we load the weather for a locally saved city
    ) = viewModelScope.launch {
        repository.loadData(city)
        getData()
    }

    private fun getData() = viewModelScope.launch {
        currentWeather.loadOrError {
            repository.getCurrentWeather().asFlow().firstOrNull()
        }
        hoursList.loadOrError {
            repository.getWeatherByHours().asFlow().firstOrNull()
        }
        daysList.loadOrError {
            repository.getWeatherByDays().asFlow().firstOrNull()
        }
    }
}