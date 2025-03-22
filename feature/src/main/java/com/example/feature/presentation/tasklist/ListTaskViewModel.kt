package com.example.feature.presentation.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Resource
import com.example.core.domain.model.TaskEntity
import com.example.core.domain.usecases.DeleteTasksUseCase
import com.example.core.domain.usecases.GetTasksUseCase
import com.example.core.domain.usecases.ReorderTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,
    private val reorderTasksUseCase: ReorderTasksUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ListTaskState>(ListTaskState.Loading)
    val state: StateFlow<ListTaskState> = _state


    fun handleIntent(intent: ListTaskIntent) {
        when (intent) {
            is ListTaskIntent.LoadTasks -> loadTasks()
            is ListTaskIntent.DeleteTask -> deleteTask(intent.taskId)
            is ListTaskIntent.ReorderTasks -> reorderTasks(intent.tasks)
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { result ->
                _state.value = when (result) {
                    is Resource.Success -> ListTaskState.Success(result.data)
                    is Resource.Error -> ListTaskState.Error(result.message)
                    is Resource.Loading -> ListTaskState.Loading
                }
            }
        }
    }

    private fun deleteTask(id: Int) {
        viewModelScope.launch {
            deleteTasksUseCase(id)
            loadTasks()
        }
    }

    private fun reorderTasks(updatedTasks: List<TaskEntity>) {
        viewModelScope.launch {
            reorderTasksUseCase(updatedTasks)
            _state.value = ListTaskState.Success(updatedTasks)
        }
    }
}
