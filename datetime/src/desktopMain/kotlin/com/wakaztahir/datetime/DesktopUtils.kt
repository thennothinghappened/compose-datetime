package com.wakaztahir.datetime

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import org.jetbrains.skia.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

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


//actual class PlatformLocalTime(var time: LocalTime) : Comparable<PlatformLocalTime> {
//    override fun compareTo(other: PlatformLocalTime): Int {
//        return time.compareTo(other.time)
//    }
//
//    actual val isAM: Boolean
//        get() = time.isAM
//    actual val hour: Int
//        get() = time.hour
//    actual val minute: Int
//        get() = time.minute
//    actual val second: Int
//        get() = time.second
//    actual val simpleHour: Int
//        get() = time.simpleHour
//    actual val nano: Int
//        get() = time.nano
//
//    actual companion object {
//
//        actual val MIN: PlatformLocalTime = PlatformLocalTime(LocalTime.MIN)
//        actual val MAX: PlatformLocalTime = PlatformLocalTime(LocalTime.MAX)
//
//        actual fun now(): PlatformLocalTime {
//            return PlatformLocalTime(LocalTime.now())
//        }
//
//        actual fun of(hour: Int, minute: Int): PlatformLocalTime {
//            return PlatformLocalTime(LocalTime.of(hour, minute))
//        }
//
//        actual fun of(hour: Int, minute: Int, second: Int): PlatformLocalTime {
//            return PlatformLocalTime(LocalTime.of(hour, minute, second))
//        }
//
//        actual fun of(hour: Int, minute: Int, second: Int, nanosecond: Int): PlatformLocalTime {
//            return PlatformLocalTime(LocalTime.of(hour, minute, second, nanosecond))
//        }
//    }
//
//    actual fun withHour(hour: Int): PlatformLocalTime {
//        return PlatformLocalTime(time.withHour(hour))
//    }
//
//    actual fun withMinute(minute: Int): PlatformLocalTime {
//        return PlatformLocalTime(time.withMinute(minute))
//    }
//
//    actual fun noSeconds(): PlatformLocalTime {
//        return PlatformLocalTime(time.noSeconds())
//    }
//
//    actual fun toPM(): PlatformLocalTime {
//        return PlatformLocalTime(time.toPM())
//    }
//
//    actual fun toAM(): PlatformLocalTime {
//        return PlatformLocalTime(time.toAM())
//    }
//
//    fun toLocalTime() : LocalTime = time
//}

internal actual fun kotlinx.datetime.LocalDate.getFirstDayOfMonth(): Int {
    return toJavaLocalDate().withDayOfMonth(1).dayOfWeek.value % 7
}

internal actual fun kotlinx.datetime.LocalDate.getNumDays(): Int {
    return toJavaLocalDate().let { it.month.length(it.isLeapYear) }
}

internal actual fun kotlinx.datetime.LocalDate.getMonthShortLocalName(): String {
    return toJavaLocalDate().month.shortLocalName
}

internal actual fun kotlinx.datetime.LocalDate.getDayOfWeekShortLocalName(): String {
    return toJavaLocalDate().dayOfWeek.shortLocalName
}

internal actual fun kotlinx.datetime.LocalDate.withDayOfMonth(dayOfMonth: Int): kotlinx.datetime.LocalDate {
    return toJavaLocalDate().withDayOfMonth(dayOfMonth).toKotlinLocalDate()
}

internal actual fun kotlinx.datetime.LocalDate.getMonthDisplayName(): String {
    return toJavaLocalDate().month.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

internal actual fun kotlinx.datetime.LocalTime.noSeconds() : kotlinx.datetime.LocalTime
        = this.toJavaLocalTime().noSeconds().toKotlinLocalTime()

internal actual val kotlinx.datetime.LocalTime.isAM : Boolean
    get() = this.toJavaLocalTime().isAM

internal actual val kotlinx.datetime.LocalTime.simpleHour : Int
    get() = this.toJavaLocalTime().simpleHour

internal actual fun kotlinx.datetime.LocalTime.withHour(hour: Int) =
    this.toJavaLocalTime().withHour(hour).toKotlinLocalTime()

internal actual fun kotlinx.datetime.LocalTime.withMinute(mins : Int) =
    this.toJavaLocalTime().withMinute(mins).toKotlinLocalTime()

internal actual fun kotlinx.datetime.LocalTime.toAM() =
    this.toJavaLocalTime().toAM().toKotlinLocalTime()

internal actual fun kotlinx.datetime.LocalTime.toPM() =
    this.toJavaLocalTime().toPM().toKotlinLocalTime()

internal actual fun getTimeRange(): ClosedRange<kotlinx.datetime.LocalTime> = LocalTime.MIN.toKotlinLocalTime()..LocalTime.MAX.toKotlinLocalTime()