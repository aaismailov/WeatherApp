package com.example.weatherapp.data.mapper

import com.example.weatherapp.data.database.modelsDB.CurrentModelDB
import com.example.weatherapp.data.network.models.WeatherDto
import com.example.weatherapp.domain.WeatherEntity


class CurrentMapper {

    fun mapDtoToModelDb(weatherDto: WeatherDto) = CurrentModelDB(
        time = weatherDto.current.lastUpdated,
        city = weatherDto.location.city,
        conditionText = weatherDto.current.condition.conditionText,
        conditionIcon = weatherDto.current.condition.conditionIcon,
        currentTemp = weatherDto.current.tempC.toString(),
        maxTemp = weatherDto.forecast.forecastForDaysList[0].day.maxTemp.toString(),
        minTemp = weatherDto.forecast.forecastForDaysList[0].day.minTemp.toString()
    )

    fun mapModelDbToEntity(dbModel: CurrentModelDB) = WeatherEntity(
        time = dbModel.time,
        conditionText = dbModel.conditionText,
        currentTemp = dbModel.currentTemp,
        maxTemp = dbModel.maxTemp,
        minTemp = dbModel.minTemp,
        conditionIcon = dbModel.conditionIcon,
        city = dbModel.city
    )

}