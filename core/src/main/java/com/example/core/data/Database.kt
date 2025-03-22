package com.example.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.dao.TaskDao
import com.example.core.domain.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}