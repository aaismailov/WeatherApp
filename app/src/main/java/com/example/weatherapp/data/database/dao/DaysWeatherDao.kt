package com.example.weatherapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.database.modelsDB.DayItemModelDb

@Dao
interface DaysWeatherDao {

    @Query("SELECT * FROM days_list")
    fun getWeatherByDays(): LiveData<List<DayItemModelDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherByDays(dayList: List<DayItemModelDb>)

    @Query("DELETE FROM days_list")
    suspend fun clearWeatherByDays()
}