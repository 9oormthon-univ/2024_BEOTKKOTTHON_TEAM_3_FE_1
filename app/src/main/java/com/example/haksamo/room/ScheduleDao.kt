package com.example.haksamo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ScheduleDao {

    @Insert
    fun insertSchedule(schedule: ScheduleEntity)

    @Query("SELECT * FROM ScheduleEntity")
    fun getAllSchedules(): List<ScheduleEntity>

    @Query("SELECT * FROM ScheduleEntity WHERE date = :date")
    fun getScheduleByDate(date: String): List<ScheduleEntity>

    @Update
    fun updateSchedule(schedule: ScheduleEntity)

    @Delete
    fun deleteSchedule(schedule: ScheduleEntity)

    @Query("DELETE FROM ScheduleEntity")
    fun deleteAllSchedules()

    @Query("SELECT * FROM ScheduleEntity WHERE id = :id")
    fun getScheduleById(id: Int): ScheduleEntity?

}

@Dao
interface SchoolDao {

    @Insert
    fun insertSchoolSchedule(schoolEntity: SchoolEntity)

    @Query("SELECT * FROM SchoolEntity")
    fun getAllSchoolSchedules(): List<SchoolEntity>

    @Query("SELECT * FROM SchoolEntity WHERE date = :date")
    fun getSchoolScheduleByDate(date: String): List<SchoolEntity>

    @Update
    fun updateSchoolSchedule(schedule: SchoolEntity)

    @Delete
    fun deleteSchoolSchedule(schedule: SchoolEntity)

    @Query("DELETE FROM SchoolEntity")
    fun deleteAllSchoolSchedules()
}