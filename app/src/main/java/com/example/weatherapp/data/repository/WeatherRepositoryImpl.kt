package com.example.weatherapp.data.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.weatherapp.data.database.AppDatabase
import com.example.weatherapp.data.mapper.CurrentMapper
import com.example.weatherapp.data.mapper.DaysMapper
import com.example.weatherapp.data.mapper.HoursMapper
import com.example.weatherapp.data.network.ApiFactory
import com.example.weatherapp.domain.WeatherEntity
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherRepositoryImpl(private val application: Application): WeatherRepository {

    private val currentWeatherDao = AppDatabase.getInstance(application).currentDao()
    private val daysWeatherDao = AppDatabase.getInstance(application).daysDao()
    private val hoursWeatherDao = AppDatabase.getInstance(application).hoursDao()

    private val apiService = ApiFactory.apiService

    private val currentMapper = CurrentMapper()
    private val daysMapper = DaysMapper()
    private val hoursMapper = HoursMapper()

    private val sharedPreferences = application.applicationContext
        .getSharedPreferences(Constants.Preferences.NAME, Context.MODE_PRIVATE)

    private val _cityPrefs = MutableStateFlow(
        sharedPreferences.getString(Constants.Preferences.CITY, "").orEmpty()
    )
    val cityPrefs: StateFlow<String> = _cityPrefs

    override suspend fun loadData(
        city: String
    ) {
        try {
            val requestResult = apiService.getForecast(q = city)
            val currentWeather = currentMapper.mapDtoToModelDb(requestResult)
            val hourList = hoursMapper.mapDtoToModelDb(requestResult)
            val dayList = daysMapper.mapDtoToModelDb(requestResult)

            currentWeatherDao.clearCurrentWeather()
            hoursWeatherDao.clearWeatherByHours()
            daysWeatherDao.clearWeatherByDays()
            currentWeatherDao.insertCurrentWeather(currentWeather)
            hoursWeatherDao.insertWeatherByHours(hourList)
            daysWeatherDao.insertWeatherByDays(dayList)

            // Save the city in preferences as the current city
            sharedPreferences.edit().putString(Constants.Preferences.CITY, city).apply()
            _cityPrefs.value = city

        } catch (e: Exception) {
            Toast.makeText(application.applicationContext, "Такого города не существует", Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun getCurrentWeather(): LiveData<WeatherEntity> {
        return Transformations.map(currentWeatherDao.getCurrentWeather()) {
            currentMapper.mapModelDbToEntity(it)
        }
    }

    override suspend fun getWeatherByDays(): LiveData<List<WeatherEntity>> {
        return Transformations.map(daysWeatherDao.getWeatherByDays()) { item ->
            item.map {
                daysMapper.mapModelDbToEntity(it)
            }
        }
    }

    override suspend fun getWeatherByHours(): LiveData<List<WeatherEntity>> {
        return Transformations.map(hoursWeatherDao.getWeatherByHours()) { item ->
            item.map {
                hoursMapper.mapModelDbToEntity(it)
            }
        }
    }
}