package com.example.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?,
    val priority: TaskPriority,
    val dueDate: Long?,
    val isCompleted: Boolean = false,
    val progress: Int = 0
)

enum class TaskPriority { LOW, MEDIUM, HIGH }
