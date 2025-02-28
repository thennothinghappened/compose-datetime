package com.wakaztahir.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wakaztahir.datetime.date.DatePicker
import com.wakaztahir.datetime.time.TimePicker
import com.wakaztahir.datetime.time.rememberTimePickerState
import kotlinx.datetime.LocalTime

/**
 * @brief Date and Time Picker Demos
 */
@Composable
fun DateTimeDialogDemo(modifier : Modifier = Modifier) = Column(modifier = modifier) {

    val dialogModifier = Modifier.padding(horizontal = 16.dp).widthIn(max = 420.dp).clip(RoundedCornerShape(4.dp))
        .background(color = MaterialTheme.colorScheme.background).padding(bottom = 8.dp)

    SimpleDialogAndButton(
        buttonText = "Date Picker"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            DatePicker(
                modifier = dialogModifier,
                onDateChange = {
                    println(it.toString())
                }
            )
        }
    }

    SimpleDialogAndButton(
        buttonText = "Time Picker"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            TimePicker(
                modifier = dialogModifier,
                onTimeChange = {
                    println(it.toString())
                }
            )
        }
    }

    SimpleDialogAndButton(
        buttonText = "Time Picker Dialog With Min/Max"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            TimePicker(
                modifier = dialogModifier, state = rememberTimePickerState(
                    timeRange = LocalTime(9, 35)..LocalTime(21, 13), is24HourClock = false
                ),
                onTimeChange = {
                    println(it.toString())
                }
            )
        }
    }

    SimpleDialogAndButton(
        buttonText = "Time Picker Dialog 24H"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            TimePicker(
                modifier = dialogModifier, state = rememberTimePickerState(is24HourClock = true),
                onTimeChange = {
                    println(it.toString())
                }
            )
        }
    }

    SimpleDialogAndButton(
        buttonText = "Time Picker Dialog 24H With Min/Max"
    ) { onDismissRequest ->
        DialogBox(onDismissRequest) {
            TimePicker(
                modifier = dialogModifier, state = rememberTimePickerState(
                    timeRange = LocalTime(9, 35)..LocalTime(21, 13), is24HourClock = true,
                ),
                onTimeChange = {
                    println(it.toString())
                }
            )
        }
    }
}