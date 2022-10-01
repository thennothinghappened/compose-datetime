package com.wakaztahir.datetime

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.math.cos
import kotlin.math.sin

// Screen Configuration

internal fun Float.getOffset(angle: Double): Offset =
    Offset((this * cos(angle)).toFloat(), (this * sin(angle)).toFloat())

@Composable
internal expect fun isSmallDevice(): Boolean

@Composable
internal expect fun isLargeDevice(): Boolean

// Canvas Functions

internal expect fun Canvas.drawText(
    text: String,
    x: Float,
    y: Float,
    color: Color,
    textSize: Float,
    angle: Float,
    radius: Float,
    isCenter: Boolean?,
    alpha: Int,
)

// LocalDate Functions

internal expect fun LocalDate.withDayOfMonth(dayOfMonth: Int): LocalDate

internal expect fun LocalDate.getFirstDayOfMonth(): Int

internal expect fun LocalDate.getNumDays(): Int

internal expect fun LocalDate.getMonthShortLocalName() : String

internal expect fun LocalDate.getDayOfWeekShortLocalName() : String

internal expect fun LocalDate.getMonthDisplayName(): String

// LocalTime Functions

internal expect fun LocalTime.noSeconds() : LocalTime

internal expect val LocalTime.isAM : Boolean

internal expect val LocalTime.simpleHour : Int

internal expect fun LocalTime.withHour(hour: Int): LocalTime

internal expect fun LocalTime.withMinute(mins : Int) : LocalTime

internal expect fun LocalTime.toAM() : LocalTime

internal expect fun LocalTime.toPM() : LocalTime

internal expect fun getTimeRange(): ClosedRange<LocalTime>