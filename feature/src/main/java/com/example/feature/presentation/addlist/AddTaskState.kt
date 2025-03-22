package com.example.feature.presentation.addlist

sealed class AddTaskState {
    object Loading : AddTaskState()
    data class Success(val message: String) : AddTaskState()
    data class Error(val message: String) : AddTaskState()
}