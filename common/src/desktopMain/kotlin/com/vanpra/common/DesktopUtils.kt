package com.vanpra.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState

@Composable
actual fun DialogBox(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onCloseRequest = onDismissRequest,state = DialogState(height = 400.dp)) {
        content()
    }
}