package com.example.feature.presentation.detailstask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.component.CustomProgressIndicator
import com.example.component.GradientCard
import com.example.core.common.Constanta.formatDate
import com.example.core.domain.model.TaskPriority
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTaskScreen(
    navController: NavController,
    taskId: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var progress by remember { mutableIntStateOf(0) }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(taskId) {
        viewModel.handleIntent(DetailIntent.LoadData(taskId))
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Task Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is DetailState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is DetailState.LoadData -> {
                    val task = (state as DetailState.LoadData).task
                    LaunchedEffect(task.progress) {
                        progress = task.progress
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        GradientCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(300.dp),
                            label = "Task Details"
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = task.description ?: "No description provided",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Priority: ${task.priority.name}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = when (task.priority) {
                                            TaskPriority.HIGH -> Color.Red
                                            TaskPriority.MEDIUM -> Color(0xFFFFA500)
                                            TaskPriority.LOW -> Color.Magenta
                                        }
                                    )

                                    Text(
                                        text = "Due: ${task.dueDate?.let { formatDate(it) } ?: "No due date"}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        CustomProgressIndicator(progress = progress)

                        Column {
                            Text(
                                "Update Progress: $progress%",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Slider(
                                value = progress.toFloat(),
                                onValueChange = { progress = it.toInt() },
                                valueRange = 0f..100f,
                                steps = 9,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    viewModel.handleIntent(
                                        DetailIntent.UpdateData(
                                            task.id,
                                            isCompleted = (progress == 100),
                                            progress
                                        )
                                    )
                                }
                            ) {
                                Text("Save Progress")
                            }

                            OutlinedButton(onClick = { navController.popBackStack() }) {
                                Text("Back")
                            }
                        }
                    }
                }

                is DetailState.SuccessUpdated -> {
                    LaunchedEffect(state) {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = (state as DetailState.SuccessUpdated).message
                            )
                        }
                        delay(1000)
                        navController.popBackStack()
                    }
                }

                is DetailState.Error -> {
                    val errorMessage = (state as DetailState.Error).message
                    Text(
                        errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
