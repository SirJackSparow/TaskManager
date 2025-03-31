package com.example.feature.presentation.navigation

sealed class Screen(val route: String) {
    object TaskList : Screen("task_list")
    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: Int) = "task_detail/$taskId"
    }

    object AddTask : Screen("add_task")
    object Settings : Screen("settings")
    object SecureScreen : Screen("secure_screen")
}