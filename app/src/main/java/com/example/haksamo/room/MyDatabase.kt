package com.example.haksamo.room


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScheduleEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
}

@Database(entities = [SchoolEntity::class], version = 1)
abstract class SchoolDatabase : RoomDatabase() {
    abstract fun SchoolDao(): SchoolDao
}

