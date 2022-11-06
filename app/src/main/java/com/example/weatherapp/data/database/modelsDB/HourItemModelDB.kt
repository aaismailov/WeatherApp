package com.example.weatherapp.data.database.modelsDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hours_list")
data class HourItemModelDB(
    @PrimaryKey
    val id: Int,
    val conditionIcon: String,
    val time: String,
    val conditionText: String,
    val temp: String
)