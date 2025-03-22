package com.example.feature.presentation.detailstask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecases.GetTaskByIdUseCase
import com.example.core.domain.usecases.UpdateTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val updateTasksUseCase: UpdateTasksUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state: StateFlow<DetailState> = _state

    fun handleIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.UpdateData -> updateData(intent)
            is DetailIntent.LoadData -> loadData(intent.taskId)
        }
    }

    private fun loadData(taskId: Int) {
        viewModelScope.launch {
            try {
                val task = getTaskByIdUseCase(taskId)
                if (task != null) {
                    _state.value = DetailState.LoadData(task)
                } else {
                    _state.value = DetailState.Error("Task not found")
                }
            } catch (e: Exception) {
                _state.value = DetailState.Error("Failed to load task: ${e.message}")
            }
        }
    }
    private fun updateData(intent: DetailIntent) {
        val data = intent as DetailIntent.UpdateData
        viewModelScope.launch {
            try {
                updateTasksUseCase(data.id, data.isCompleted, data.progress)
                _state.value = DetailState.SuccessUpdated("Data updated successfully")
            } catch (e: Exception) {
                _state.value = DetailState.Error("Failed to update data: ${e.message}")
            }

        }
    }
}