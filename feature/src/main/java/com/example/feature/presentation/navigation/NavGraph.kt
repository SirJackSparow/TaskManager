package com.example.feature.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feature.presentation.addlist.AddTaskManagerScreen
import com.example.feature.presentation.tasklist.TaskListScreen

@Composable
fun TaskManagerNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.TaskList.route) {
        composable(Screen.TaskList.route) {
            TaskListScreen(navController)
        }
        composable(Screen.AddTask.route) {
            AddTaskManagerScreen(navController)
        }
        composable(Screen.Settings.route) {
            //SettingsScreen(navController)
        }
        composable(Screen.TaskDetail.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
            if (taskId != null) {
                //TaskDetailScreen(navController, taskId)
            }
        }
    }
}