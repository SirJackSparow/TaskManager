package com.example.feature.presentation.navigation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feature.presentation.addlist.AddTaskManagerScreen
import com.example.feature.presentation.detailstask.DetailTaskScreen
import com.example.feature.presentation.securescreen.SecureScreen
import com.example.feature.presentation.settings.SettingsScreen
import com.example.feature.presentation.tasklist.TaskListScreen

@Composable
fun TaskManagerNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.TaskList.route) {
        composable(
            Screen.TaskList.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300)
                )
            }) {
            TaskListScreen(navController)
        }
        composable(Screen.AddTask.route) {
            AddTaskManagerScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(Screen.SecureScreen.route) {
            //SecureScreen(context = context)
        }
        composable(Screen.TaskDetail.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
            if (taskId != null) {
                DetailTaskScreen(navController, taskId)
            }
        }
    }
}