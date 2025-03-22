package com.example.feature.presentation.tasklist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.component.FilterDropdown
import com.example.component.GradientCard
import com.example.component.SortDropdown
import com.example.feature.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController, viewModel: ListTaskViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var isFabVisible by remember { mutableStateOf(false) }

    // Sorting & Filtering state
    var selectedSortOption by remember { mutableStateOf("Priority") }
    var selectedFilter by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ListTaskIntent.LoadTasks)
        isFabVisible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "Task List") }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isFabVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddTask.route) },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text("+", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    ) { padding ->

        when (state) {
            is ListTaskState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            is ListTaskState.Success -> {
                val tasks = (state as ListTaskState.Success).taskList
                Column(modifier = Modifier.padding(padding)) {

                    // Sorting and Filtering UI
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Sorting Dropdown
                        SortDropdown(
                            selectedOption = selectedSortOption,
                            onOptionSelected = { selectedSortOption = it }
                        )

                        // Filtering Dropdown
                        FilterDropdown(
                            selectedFilter = selectedFilter,
                            onFilterSelected = { selectedFilter = it }
                        )
                    }

                    val sortedFilteredTasks = tasks
                        .filter {
                            when (selectedFilter) {
                                "Completed" -> it.isCompleted
                                "Pending" -> !it.isCompleted
                                else -> true
                            }
                        }
                        .sortedBy {
                            when (selectedSortOption) {
                                "Priority" -> it.priority.toString()
                                "Due Date" -> it.dueDate.toString()
                                "Alphabetical" -> it.title
                                else -> it.priority.toString()
                            }
                        }

                    if (sortedFilteredTasks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No tasks available",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF6200EE),
                                            Color(0xFF03DAC5)
                                        )
                                    )
                                )
                            )
                        }
                    } else {
                        LazyColumn {
                            items(sortedFilteredTasks, key = { it.id }) { task ->
                                val dismissState = rememberSwipeToDismissBoxState(
                                    confirmValueChange = { dismissValue ->
                                        if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                            viewModel.handleIntent(ListTaskIntent.DeleteTask(task.id))
                                            true
                                        } else false
                                    }
                                )
                                SwipeToDismissBox(
                                    state = dismissState,
                                    backgroundContent = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp)
                                                .background(
                                                    Brush.horizontalGradient(
                                                        colors = listOf(
                                                            MaterialTheme.colorScheme.surface,
                                                            MaterialTheme.colorScheme.error
                                                        )
                                                    ),
                                                    shape = MaterialTheme.shapes.medium
                                                ),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            Text(
                                                "Delete",
                                                color = Color.White,
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.padding(end = 20.dp)
                                            )
                                        }
                                    },
                                    content = {
                                        GradientCard(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth()
                                                .clickable {
                                                    navController.navigate(
                                                        Screen.TaskDetail.createRoute(task.id)
                                                    )
                                                }
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .padding(16.dp)
                                                    .fillMaxSize()
                                            ) {
                                                Text(task.title, style = MaterialTheme.typography.titleLarge)
                                                Text(
                                                    task.description ?: "",
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            is ListTaskState.Error -> Text(
                text = (state as ListTaskState.Error).message,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
