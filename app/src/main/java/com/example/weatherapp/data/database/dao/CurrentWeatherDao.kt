package com.example.weatherapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.database.modelsDB.CurrentModelDB

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * FROM current_weather")
    fun getCurrentWeather(): LiveData<CurrentModelDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentModelDB: CurrentModelDB)

    @Query("DELETE FROM current_weather")
    suspend fun clearCurrentWeather()
}