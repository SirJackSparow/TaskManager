package com.example.core.di

import com.example.core.data.repositories.TaskRepository
import com.example.core.data.repositories.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindRepo(repoImpl: TaskRepositoryImpl): TaskRepository
}