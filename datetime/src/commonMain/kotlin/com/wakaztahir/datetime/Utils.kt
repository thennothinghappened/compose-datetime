package com.wakaztahir.datetime

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
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

// LocalDate And LocalTime Functions

internal expect fun LocalDate.withDayOfMonth(dayOfMonth: Int): LocalDate

internal expect fun LocalDate.getFirstDayOfMonth(): Int

internal expect fun LocalDate.getNumDays(): Int

internal expect fun LocalDate.getMonthShortLocalName() : String

internal expect fun LocalDate.getDayOfWeekShortLocalName() : String

internal expect fun LocalDate.getMonthDisplayName(): String

expect class PlatformLocalTime : Comparable<PlatformLocalTime> {
    val isAM: Boolean
    val hour: Int
    val minute: Int
    val second : Int
    val simpleHour : Int
    val nano : Int

    companion object {
        val MIN: PlatformLocalTime
        val MAX: PlatformLocalTime
        fun now(): PlatformLocalTime
        fun of(hour: Int, minute: Int): PlatformLocalTime
        fun of(hour: Int, minute: Int, second: Int): PlatformLocalTime
        fun of(hour: Int, minute: Int, second: Int, nanosecond: Int): PlatformLocalTime
    }

    fun withHour(hour: Int): PlatformLocalTime
    fun withMinute(minute: Int): PlatformLocalTime
    fun toAM(): PlatformLocalTime
    fun toPM(): PlatformLocalTime
    fun noSeconds(): PlatformLocalTime
}