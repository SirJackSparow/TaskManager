package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.Database
import com.example.core.data.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java, "task_db"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: Database): TaskDao = database.taskDao()
}
