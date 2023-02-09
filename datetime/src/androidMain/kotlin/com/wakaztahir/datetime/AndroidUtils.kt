package com.wakaztahir.datetime

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.datetime.toJavaLocalDate
import java.time.format.TextStyle
import java.util.*

// Screen Configuration

@Composable
internal actual fun isSmallDevice(): Boolean {
    return LocalConfiguration.current.screenWidthDp <= 360
}

internal actual fun kotlinx.datetime.LocalDate.getNumDays(): Int {
    return toJavaLocalDate().let { it.month.length(it.isLeapYear) }
}

internal actual fun kotlinx.datetime.LocalDate.getMonthShortLocalName(): String {
    return toJavaLocalDate().month.getDisplayName(TextStyle.SHORT, (Locale.getDefault()))
}

internal actual fun kotlinx.datetime.LocalDate.getDayOfWeekShortLocalName(): String {
    return toJavaLocalDate().dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
}
internal actual fun kotlinx.datetime.LocalDate.getMonthDisplayName(): String {
    return toJavaLocalDate().month.getDisplayName(TextStyle.FULL, Locale.getDefault())
}