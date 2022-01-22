package com.vanpra.composematerialdialogs.datetime

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import org.jetbrains.skia.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal actual fun rememberScreenConfiguration(): ScreenConfiguration {
    return remember {
        ScreenConfiguration(
            screenWidthDp = 600,
            screenHeightDp = 400
        )
    }
}

@Composable
internal actual fun isSmallDevice(): Boolean {
    return false
}

@Composable
internal actual fun isLargeDevice(): Boolean {
    return true
}

// todo This function needs to be corrected
internal actual fun Canvas.drawText(
    text: String,
    x: Float,
    y: Float,
    color: Color,
    textSize: Float,
    angle: Float,
    radius: Float,
    isCenter: Boolean?,
    alpha: Int,
) {
    val outerText = Paint()
    outerText.color = color.toAwtColor()

    val font = Font()

    nativeCanvas.drawTextBlob(
        blob = TextBlobBuilder().apply {
            appendRun(font = font, text = text, x = 0f, y = 0f)
        }.build()!!,
        x = x + (radius * cos(angle)),
        y = y + (radius * sin(angle)) + (abs(font.metrics.height)) / 2,
        paint = Paint()
    )

}

internal fun Color.toAwtColor(): Int {
    return Color4f(red, green, blue, alpha).toColor()
}

// Horizontal Pager
actual class PlatformPagerState(val listState : LazyListState) {
    var internalPageCount: Int = 0
        internal set
    actual var currentPage: Int by mutableStateOf(0)

    actual suspend fun scrollToPage(page: Int, pageOffset: Float) {
        listState.scrollToItem(page)
    }

    actual suspend fun animateScrollToPage(page: Int, pageOffset: Float) {
        listState.animateScrollToItem(page)
    }
}

actual val PlatformPagerState.platformPageCount: Int
    get() = internalPageCount

actual class PlatformLocalDate(val date: LocalDate) {
    actual val year: Int
        get() = date.year
    actual val month: Int
        get() = date.month.value
    actual val dayOfMonth: Int
        get() = date.dayOfMonth
    actual val monthValue: Int
        get() = date.monthValue
    actual val dayOfYear: Int
        get() = date.dayOfYear
    actual val dayOfWeekValue: Int
        get() = date.dayOfWeek.value
    actual val isLeapYear: Boolean
        get() = date.isLeapYear

    actual fun withDayOfMonth(dayOfMonth: Int): PlatformLocalDate {
        return PlatformLocalDate(date.withDayOfMonth(dayOfMonth))
    }

    actual fun getMonthShortLocalName(): String {
        return date.month.shortLocalName
    }

    actual fun getMonthDisplayName(): String {
        return date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    actual fun getDayOfWeekShortLocalName(): String {
        return date.dayOfWeek.shortLocalName
    }

    actual companion object {

        actual val MIN: PlatformLocalDate = PlatformLocalDate(LocalDate.MIN)
        actual val MAX: PlatformLocalDate = PlatformLocalDate(LocalDate.MAX)

        actual fun now(): PlatformLocalDate {
            return PlatformLocalDate(LocalDate.now())
        }

        actual fun of(year: Int, month: Int, dayOfMonth: Int): PlatformLocalDate {
            return PlatformLocalDate(LocalDate.of(year, month, dayOfMonth))
        }
    }

    internal actual fun getFirstDayOfMonth(): Int {
        return date.withDayOfMonth(1).dayOfWeek.value % 7
    }

    internal actual fun getNumDays(): Int {
        return date.month.length(date.isLeapYear)
    }

    fun toLocalDate() : LocalDate = date
}

actual class PlatformLocalTime(var time: LocalTime) : Comparable<PlatformLocalTime> {
    override fun compareTo(other: PlatformLocalTime): Int {
        return time.compareTo(other.time)
    }

    actual val isAM: Boolean
        get() = time.isAM
    actual val hour: Int
        get() = time.hour
    actual val minute: Int
        get() = time.minute
    actual val second: Int
        get() = time.second
    actual val simpleHour: Int
        get() = time.simpleHour
    actual val nano: Int
        get() = time.nano

    actual companion object {

        actual val MIN: PlatformLocalTime = PlatformLocalTime(LocalTime.MIN)
        actual val MAX: PlatformLocalTime = PlatformLocalTime(LocalTime.MAX)

        actual fun now(): PlatformLocalTime {
            return PlatformLocalTime(LocalTime.now())
        }

        actual fun of(hour: Int, minute: Int): PlatformLocalTime {
            return PlatformLocalTime(LocalTime.of(hour, minute))
        }

        actual fun of(hour: Int, minute: Int, second: Int): PlatformLocalTime {
            return PlatformLocalTime(LocalTime.of(hour, minute, second))
        }

        actual fun of(hour: Int, minute: Int, second: Int, nanosecond: Int): PlatformLocalTime {
            return PlatformLocalTime(LocalTime.of(hour, minute, second, nanosecond))
        }
    }

    actual fun withHour(hour: Int): PlatformLocalTime {
        return PlatformLocalTime(time.withHour(hour))
    }

    actual fun withMinute(minute: Int): PlatformLocalTime {
        return PlatformLocalTime(time.withMinute(minute))
    }

    actual fun noSeconds(): PlatformLocalTime {
        return PlatformLocalTime(time.noSeconds())
    }

    actual fun toPM(): PlatformLocalTime {
        return PlatformLocalTime(time.toPM())
    }

    actual fun toAM(): PlatformLocalTime {
        return PlatformLocalTime(time.toAM())
    }

    fun toLocalTime() : LocalTime = time
}