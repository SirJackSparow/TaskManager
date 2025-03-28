package com.example.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressIndicator(progress: Int) {
    val animatedProgress = animateFloatAsState(
        targetValue = progress / 100f,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        label = "progressAnimation"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Progress: $progress%",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
            CircularProgressIndicator(
                progress = animatedProgress.value,
                modifier = Modifier.size(100.dp),
                strokeWidth = 8.dp,
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.Gray
            )
            Text("${progress}%", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

