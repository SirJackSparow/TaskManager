package com.example.component

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberDragDropState(
    onSwap: (Int, Int) -> Unit
): DragDropState {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    return remember {
        DragDropState(listState, onSwap, scope)
    }
}

class DragDropState(
    val listState: LazyListState,
    private val onSwap: (Int, Int) -> Unit,
    private val scope: CoroutineScope
) {
    var draggedIndex by mutableStateOf<Int?>(null)
    var overIndex by mutableStateOf<Int?>(null)

    fun startDrag(index: Int) {
        draggedIndex = index
    }

    fun updateDrag(targetIndex: Int) {
        if (draggedIndex != null && targetIndex != draggedIndex) {
            overIndex = targetIndex
            onSwap(draggedIndex!!, targetIndex)
            draggedIndex = targetIndex
        }
    }

    fun stopDrag() {
        draggedIndex = null
        overIndex = null
    }
}

fun Modifier.draggableItemModifier(index: Int, state: DragDropState) = this
    .pointerInput(Unit) {
        detectDragGestures(
            onDragStart = { state.startDrag(index) },
            onDrag = { change, _ ->
                change.consume()
                val targetIndex =
                    (index + 1).coerceAtMost(state.listState.layoutInfo.totalItemsCount - 1)
                state.updateDrag(targetIndex)
            },
            onDragEnd = { state.stopDrag() },
            onDragCancel = { state.stopDrag() }
        )
    }


