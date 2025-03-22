package com.example.feature.presentation.addlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.TaskEntity
import com.example.core.domain.usecases.InsertTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val insertTasksUseCase: InsertTasksUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AddTaskState>(AddTaskState.Loading)
    val state: MutableStateFlow<AddTaskState> = _state


    fun handleIntent(intent: AddTaskIntent) {
        when (intent) {
            is AddTaskIntent.AddData -> addTask(intent.data)
        }
    }


    private fun addTask(entity: TaskEntity) {
        viewModelScope.launch {
            try {
                insertTasksUseCase.invoke(entity)
                _state.value = AddTaskState.Success("${entity.title} added successfully")
            } catch (e: Exception) {
                _state.value = AddTaskState.Error("Failed to add task: ${e.message}")
            }
        }
    }
}