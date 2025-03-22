package com.example.core.di

import com.example.core.data.repositories.TaskRepository
import com.example.core.domain.usecases.InsertTasksUseCase
import com.example.core.domain.usecases.GetTasksUseCase
import com.example.core.domain.usecases.ReorderTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun provideGetTasksUseCase(repository: TaskRepository): GetTasksUseCase {
        return GetTasksUseCase(repository)
    }

    @Provides
    fun provideInsertTaskUseCase(repository: TaskRepository): InsertTasksUseCase {
        return InsertTasksUseCase(repository)
    }

    @Provides
    fun provideUpdateOrder(repository: TaskRepository): ReorderTasksUseCase {
        return ReorderTasksUseCase(repository)
    }
}
