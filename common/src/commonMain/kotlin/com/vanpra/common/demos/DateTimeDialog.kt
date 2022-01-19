package com.vanpra.common.demos

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vanpra.common.DialogAndShowButton
import com.vanpra.common.DialogBox
import com.vanpra.common.SimpleDialogAndButton
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogButtons
import com.vanpra.composematerialdialogs.datetime.PlatformLocalTime
import com.vanpra.composematerialdialogs.datetime.date.DatePicker
import com.vanpra.composematerialdialogs.datetime.time.*

/**
 * @brief Date and Time Picker Demos
 */
@Composable
fun DateTimeDialogDemo() {
    
    val dialogModifier = Modifier.padding(horizontal = 16.dp).widthIn(max = 420.dp).clip(RoundedCornerShape(4.dp))
        .background(color = MaterialTheme.colors.background).padding(bottom = 8.dp)

    SimpleDialogAndButton(
        buttonText = "Date Picker"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            DatePicker(
                modifier = dialogModifier,
            )
        }
    }

    SimpleDialogAndButton(
        buttonText = "Time Picker"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            TimePicker(
                modifier = dialogModifier,
            )
        }
    }

    SimpleDialogAndButton(
        buttonText = "Time Picker Dialog With Min/Max"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            TimePicker(
                modifier = dialogModifier,
                state = rememberTimePickerState(
                    timeRange = PlatformLocalTime.of(9, 35)..PlatformLocalTime.of(21, 13),
                    is24HourClock = false
                )
            )
        }
    }

    SimpleDialogAndButton(
        buttonText = "Time Picker Dialog 24H"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            TimePicker(
                modifier = dialogModifier,
                state = rememberTimePickerState(is24HourClock = true)
            )
        }
    }

    SimpleDialogAndButton(
        buttonText = "Time Picker Dialog 24H With Min/Max"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            TimePicker(
                modifier = dialogModifier,
                state = rememberTimePickerState(
                    timeRange = PlatformLocalTime.of(9, 35)..PlatformLocalTime.of(21, 13),
                    is24HourClock = true
                )
            )
        }
    }


    DialogAndShowButton(
        buttonText = "Date Picker with Material Dialog"
    ) {
        DatePicker()
    }

    DialogAndShowButton(
        buttonText = "Time Picker with Material Dialog"
    ) {
        TimePicker()
    }

}

@Composable
private fun MaterialDialogButtons.defaultDateTimeDialogButtons() {
    positiveButton("Ok")
    negativeButton("Cancel")
}
