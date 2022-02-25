package com.wakaztahir.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
actual fun DialogBox(
    onDismissRequest : ()->Unit,
    content : @Composable ()->Unit
){
    Dialog(onDismissRequest = onDismissRequest,properties = DialogProperties(usePlatformDefaultWidth = false)){
        content()
    }
}