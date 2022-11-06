package com.example.weatherapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.database.modelsDB.HourItemModelDB

@Dao
interface HoursWeatherDao {

    @Query("SELECT * FROM hours_list")
    fun getWeatherByHours(): LiveData<List<HourItemModelDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherByHours(hourList: List<HourItemModelDB>)

    @Query("DELETE FROM hours_list")
    suspend fun clearWeatherByHours()
}