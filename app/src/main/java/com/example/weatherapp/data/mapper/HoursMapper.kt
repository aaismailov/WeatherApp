package com.example.weatherapp.data.mapper

import com.example.weatherapp.data.database.modelsDB.HourItemModelDB
import com.example.weatherapp.data.network.models.WeatherDto
import com.example.weatherapp.domain.WeatherEntity
import com.example.weatherapp.utils.Constants

class HoursMapper {

    fun mapDtoToModelDb(weatherDto: WeatherDto) =
        weatherDto.forecast.forecastForDaysList[0].forecastForHoursList.map {
            HourItemModelDB(
                id = it.idHour,
                time = it.time,
                conditionText = it.condition.conditionText,
                conditionIcon = it.condition.conditionIcon,
                temp = it.tempC.toString()
            )
        }

    fun mapModelDbToEntity(item: HourItemModelDB) = WeatherEntity(
        city = Constants.UNDEFINED_CITY,
        time = item.time,
        conditionText = item.conditionText,
        currentTemp = item.temp,
        maxTemp = Constants.UNDEFINED_MAX_TEMP,
        minTemp = Constants.UNDEFINED_MIN_TEMP,
        conditionIcon = item.conditionIcon
    )

}