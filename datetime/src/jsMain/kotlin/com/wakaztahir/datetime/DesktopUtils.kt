package com.wakaztahir.datetime

import androidx.compose.runtime.Composable
import kotlinx.datetime.number
import kotlin.js.Date
import kotlin.js.dateLocaleOptions

@Composable
internal actual fun isSmallDevice(): Boolean {
    return false
}

internal actual fun kotlinx.datetime.LocalDate.getNumDays(): Int {
    return Date(year,month.number,0).getDate().toInt()
}

internal actual fun kotlinx.datetime.LocalDate.getMonthShortLocalName(): String {
    return Date(year,month.number,dayOfMonth).toLocaleString("default", dateLocaleOptions { month = "short" })
}

internal actual fun kotlinx.datetime.LocalDate.getDayOfWeekShortLocalName(): String {
    return Date(year,month.number,dayOfMonth).toLocaleString("default", dateLocaleOptions {  weekday =  "long" })
}

internal actual fun kotlinx.datetime.LocalDate.getMonthDisplayName(): String {
    return Date("1999-$monthNumber-15").toLocaleString("en-us",dateLocaleOptions { month = "long" })
}