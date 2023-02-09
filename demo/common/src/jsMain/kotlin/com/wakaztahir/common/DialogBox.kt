package com.wakaztahir.common

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun Popup(
    onDismissRequest: () -> Unit,
    alignment: Alignment,
    content: @Composable () -> Unit
) {
    androidx.compose.ui.window.Popup(alignment = alignment, content = content, onDismissRequest = onDismissRequest)
}

@Composable
fun PopupDialogBox(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().zIndex(80f)) {
        Box(
            modifier = Modifier.fillMaxSize().zIndex(90f).pointerInput(null) {
                detectTapGestures {
                    onDismissRequest()
                }
            }.background(color = MaterialTheme.colorScheme.onBackground.copy(.15f))
        )
        Popup(onDismissRequest = onDismissRequest, alignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .zIndex(100f)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        shape = MaterialTheme.shapes.medium
                    )
                    .then(modifier)
            ) {
                content()
            }
        }
    }
}

@Composable
actual fun DialogBox(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) = PopupDialogBox(onDismissRequest = onDismissRequest, content = { content() })