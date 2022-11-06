package com.example.weatherapp.data.mapper

import com.example.weatherapp.data.database.modelsDB.DayItemModelDb
import com.example.weatherapp.data.network.models.WeatherDto
import com.example.weatherapp.domain.WeatherEntity
import com.example.weatherapp.utils.Constants

class DaysMapper {

    fun mapDtoToModelDb(weatherDto: WeatherDto) =
        weatherDto.forecast.forecastForDaysList.map {
            DayItemModelDb(
                id = it.idDay,
                city = Constants.UNDEFINED_CITY,
                time = it.date,
                conditionText = it.day.condition.conditionText,
                conditionIcon = it.day.condition.conditionIcon,
                maxTemp = it.day.maxTemp.toString(),
                minTemp = it.day.minTemp.toString(),
                temp = it.day.averageTemp.toString()
            )
        }

    fun mapModelDbToEntity(item: DayItemModelDb) = WeatherEntity(
        city = Constants.UNDEFINED_CITY,
        time = item.time,
        conditionText = item.conditionText,
        currentTemp = Constants.UNDEFINED_CURRENT_TEMP,
        maxTemp = item.maxTemp,
        minTemp = item.minTemp,
        conditionIcon = item.conditionIcon
    )
}