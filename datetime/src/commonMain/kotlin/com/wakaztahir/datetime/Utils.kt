package com.wakaztahir.datetime

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.isoDayNumber
import kotlin.math.*

// Screen Configuration

internal fun Float.getOffset(angle: Double): Offset =
    Offset((this * cos(angle)).toFloat(), (this * sin(angle)).toFloat())

@Composable
internal expect fun isSmallDevice(): Boolean

// Canvas Functions
@OptIn(ExperimentalTextApi::class, ExperimentalUnitApi::class)
internal fun DrawScope.drawText(
    textMeasurer: TextMeasurer,
    text: String,
    x: Float,
    y: Float,
    color: Color,
    textSize: Float,
    angle: Float,
    radius: Float,
    alpha: Int,
) {
    val measured = textMeasurer.measure(buildAnnotatedString {
        pushStyle(
            SpanStyle(
                fontSize = TextUnit(textSize / 16f, TextUnitType.Em)
            )
        )
        append(text)
        pop()
    })
    drawText(
        measured,
        topLeft = Offset(
            x = x + (radius * cos(angle)) - measured.size.width / 2f,
            y = y + (radius * sin(angle)) - (measured.size.height / 2f)
        ),
        color = color,
        alpha = max(0f, min(alpha, 255).toFloat()) / 255f,
    )
}

// LocalDate Functions

internal fun LocalDate.withDayOfMonth(dayOfMonth: Int): LocalDate {
    if(this.dayOfMonth == dayOfMonth) return this
    return LocalDate(year = year,month = month,dayOfMonth = dayOfMonth)
}

internal fun LocalDate.getFirstDayOfMonth(): Int {
    return withDayOfMonth(1).dayOfWeek.isoDayNumber % 7
}

internal expect fun LocalDate.getNumDays(): Int

internal expect fun LocalDate.getMonthShortLocalName(): String

internal expect fun LocalDate.getDayOfWeekShortLocalName(): String

internal expect fun LocalDate.getMonthDisplayName(): String

// LocalTime Functions

internal fun LocalTime.noSeconds(): LocalTime {
    return LocalTime(hour = hour, minute = minute, second = 0, nanosecond = 0)
}

internal val LocalTime.isAM: Boolean get() = this.hour in 0..11

internal val LocalTime.simpleHour: Int
    get() {
        val tempHour = this.hour % 12
        return if (tempHour == 0) 12 else tempHour
    }

internal fun LocalTime.toAM(): LocalTime {
    return if (this.isAM) this else this.withHour(hour - 12)
}

internal fun LocalTime.toPM(): LocalTime {
    return if (!this.isAM) this else this.withHour(hour + 12)
}

internal fun LocalTime.withHour(hour: Int): LocalTime {
    if(this.hour == hour) return this
    return LocalTime(hour = hour,minute = minute,second = second,nanosecond = nanosecond)
}

internal fun LocalTime.withMinute(mins: Int): LocalTime {
    if(this.minute == mins) return this
    return LocalTime(hour = hour,minute = mins,second = second,nanosecond = nanosecond)
}

// The minimum supported LocalTime, '00:00'. This is the time of midnight at the start of the day.
internal val MinLocalTime = LocalTime(0,0,0,0)
// The maximum supported LocalTime, '23:59:59.999999999'. This is the time just before midnight at the end of the day.
internal val MaxLocalTime = LocalTime(23, 59, 59, 999_999_999)
internal fun getTimeRange(): ClosedRange<LocalTime> = MinLocalTime..MaxLocalTime