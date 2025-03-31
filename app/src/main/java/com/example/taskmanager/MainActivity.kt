//package com.example.taskmanager
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.feature.presentation.navigation.TaskManagerNavGraph
//import com.example.feature.presentation.settings.SettingsViewModel
//import com.example.taskmanager.ui.theme.TaskManagerTheme
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//
//            val viewModel: SettingsViewModel = hiltViewModel()
//            val themeMode by viewModel.themeMode.collectAsState()
//            TaskManagerTheme(darkTheme = themeMode == "dark") {
//                TaskManagerNavGraph(this)
//            }
//        }
//    }
//}


/*
this is for biometric activity must extends AppCompatActivity and change the theme to Theme.AppCOmpat*/
