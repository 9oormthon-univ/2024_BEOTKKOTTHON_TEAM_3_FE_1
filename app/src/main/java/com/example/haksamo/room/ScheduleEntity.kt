package com.example.haksamo.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ScheduleEntity")
data class ScheduleEntity (
    val date: String, // 일정의 날짜
    var schedule: String,
    var location: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity(tableName = "SchoolEntity")
data class SchoolEntity (
    val date: String, // 일정의 날짜
    val schedule: String,
    val location: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}