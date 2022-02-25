package com.wakaztahir.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @brief Collection of Material Dialog Demos
 */
@Composable
fun DialogDemos() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Material Dialogs Demo")
                }
            )
        }
    ) {
        DateTimeDialogDemo()
    }
}
