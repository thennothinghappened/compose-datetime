package com.vanpra.composematerialdialogs.datetime.date

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.vanpra.composematerialdialogs.datetime.*
import kotlinx.coroutines.launch

/**
 * @param initialDate time to be shown to the user when the dialog is first shown.
 * Defaults to the current date if this is not set
 * @param yearRange the range of years the user should be allowed to pick from
 */
@Composable
fun rememberDatePickerState(
    initialDate: PlatformLocalDate = PlatformLocalDate.now(),
    yearRange: IntRange = IntRange(1900, 2100),
) : DatePickerState {
    return remember {
        DatePickerState(initialDate, yearRange)
    }
}

/**
 * @brief A date picker body layout
 * @param title Title of the picker
 * @param state see [DatePickerState]
 * @param colors see [DatePickerColors]
 * @param onDateChange callback with a LocalDateTime object when the user completes their input
 */
@Composable
fun DatePicker(
    modifier : Modifier = Modifier,
    title: String = "SELECT DATE",
    state : DatePickerState = rememberDatePickerState(),
    colors: DatePickerColors = DatePickerDefaults.colors(),
    onDateChange: (PlatformLocalDate) -> Unit = {}
) {

    DisposableEffect(state.selected) {
        onDateChange(state.selected)
        onDispose { }
    }

    DatePickerImpl(modifier = modifier,title = title, state = state,colors)
}

@Composable
internal fun DatePickerImpl(modifier : Modifier = Modifier,title: String, state: DatePickerState,colors: DatePickerColors) {
    val pagerState = rememberPlatformPagerState(
        initialPage = (state.selected.year - state.yearRange.first) * 12 + state.selected.monthValue - 1
    )

    Column(modifier.fillMaxWidth()) {
        CalendarHeader(title, state,colors)
        PlatformHorizontalPager(
            count = (state.yearRange.last - state.yearRange.first + 1) * 12,
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.height(336.dp)
        ) { page ->
            val viewDate = remember {
                PlatformLocalDate.of(
                    state.yearRange.first + page / 12,
                    page % 12 + 1,
                    1
                )
            }

            Column {
                CalendarViewHeader(viewDate, state, pagerState)
                Box {
                    androidx.compose.animation.AnimatedVisibility(
                        state.yearPickerShowing,
                        modifier = Modifier
                            .zIndex(0.7f)
                            .clipToBounds(),
                        enter = slideInVertically(initialOffsetY = { -it }),
                        exit = slideOutVertically(targetOffsetY = { -it })
                    ) {
                        YearPicker(
                            viewDate = viewDate,
                            state = state,
                            colors = colors,
                            pagerState = pagerState
                        )
                    }

                    CalendarView(viewDate, state,colors)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun YearPicker(
    modifier : Modifier = Modifier,
    viewDate: PlatformLocalDate,
    state: DatePickerState,
    colors: DatePickerColors,
    pagerState: PlatformPagerState,
) {
    val gridState = rememberLazyListState((viewDate.year - state.yearRange.first) / 3)
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(3),
        state = gridState
    ) {
        itemsIndexed(state.yearRange.toList()) { _, item ->
            val selected = remember { item == viewDate.year }
            YearPickerItem(year = item, selected = selected, colors = colors) {
                if (!selected) {
                    coroutineScope.launch {
                        pagerState.scrollToPage(
                            pagerState.currentPage + (item - viewDate.year) * 12
                        )
                    }
                }
                state.yearPickerShowing = false
            }
        }
    }
}

@Composable
private fun YearPickerItem(
    year: Int,
    selected: Boolean,
    colors: DatePickerColors,
    onClick: () -> Unit
) {
    Box(Modifier.size(88.dp, 52.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(72.dp, 36.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colors.backgroundColor(selected).value)
                .clickable(
                    onClick = onClick,
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                year.toString(),
                style = TextStyle(
                    color = colors.textColor(selected).value,
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
private fun CalendarViewHeader(
    viewDate: PlatformLocalDate,
    state: DatePickerState,
    pagerState: PlatformPagerState
) {
    val coroutineScope = rememberCoroutineScope()
    val month = remember { viewDate.getMonthDisplayName() }
    val yearDropdownIcon = remember(state.yearPickerShowing) {
        if (state.yearPickerShowing)
            Icons.Default.KeyboardArrowUp // todo fix : ArrowDropUp is missing
        else Icons.Default.ArrowDropDown
    }

    Box(
        Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
            .height(24.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxHeight()
                .align(Alignment.CenterStart)
                .clickable(onClick = { state.yearPickerShowing = !state.yearPickerShowing })
        ) {
            Text(
                "$month ${viewDate.year}",
                modifier = Modifier
                    .paddingFromBaseline(top = 16.dp)
                    .wrapContentSize(Alignment.Center),
                style = TextStyle(fontSize = 14.sp, fontWeight = W600),
                color = MaterialTheme.colors.onBackground
            )

            Spacer(Modifier.width(4.dp))
            Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                Icon(
                    yearDropdownIcon,
                    contentDescription = "Year Selector",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }

        Row(
            Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Month",
                modifier = Modifier
                    .testTag("dialog_date_prev_month")
                    .size(24.dp)
                    .clickable(
                        onClick = {
                            coroutineScope.launch {
                                if (pagerState.currentPage - 1 >= 0)
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    ),
                tint = MaterialTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.width(24.dp))

            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Next Month",
                modifier = Modifier
                    .testTag("dialog_date_next_month")
                    .size(24.dp)
                    .clickable(
                        onClick = {
                            coroutineScope.launch {
                                if (pagerState.currentPage + 1 < pagerState.platformPageCount)
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    ),
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CalendarView(viewDate: PlatformLocalDate, state: DatePickerState,colors: DatePickerColors) {
    Column(
        Modifier
            .padding(start = 12.dp, end = 12.dp)
            .testTag("dialog_date_calendar")
    ) {
        DayOfWeekHeader()
        val calendarDatesData = remember { viewDate.getDates() }
        val datesList = remember { IntRange(1, calendarDatesData.second).toList() }
        val possibleSelected = remember(state.selected) {
            viewDate.year == state.selected.year && viewDate.month == state.selected.month
        }

        LazyVerticalGrid(cells = GridCells.Fixed(7), modifier = Modifier.height(240.dp)) {
            for (x in 0 until calendarDatesData.first) {
                item { Box(Modifier.size(40.dp)) }
            }

            items(datesList) {
                val selected = remember(state.selected) {
                    possibleSelected && it == state.selected.dayOfMonth
                }

                DateSelectionBox(it, selected, colors) {
                    state.selected = viewDate.withDayOfMonth(it)
                }
            }
        }
    }
}

@Composable
private fun DateSelectionBox(
    date: Int,
    selected: Boolean,
    colors: DatePickerColors,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .testTag("dialog_date_selection_$date")
            .size(40.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                onClick = onClick,
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            date.toString(),
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(colors.backgroundColor(selected).value)
                .wrapContentSize(Alignment.Center),
            style = TextStyle(
                color = colors.textColor(selected).value,
                fontSize = 12.sp
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DayOfWeekHeader() {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LazyVerticalGrid(cells = GridCells.Fixed(7)) {
            DatePickerState.dayHeaders.forEach { it ->
                item {
                    Box(Modifier.size(40.dp)) {
                        Text(
                            it,
                            modifier = Modifier
                                .alpha(0.8f)
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center),
                            style = TextStyle(fontSize = 14.sp, fontWeight = W600),
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarHeader(title: String, state: DatePickerState,colors: DatePickerColors) {
    val month = remember(state.selected) { state.selected.getMonthShortLocalName() }
    val day = remember(state.selected) { state.selected.getDayOfWeekShortLocalName() }

    Box(
        Modifier
            .background(colors.headerBackgroundColor)
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(start = 24.dp, end = 24.dp)) {
            Text(
                text = title,
                modifier = Modifier.paddingFromBaseline(top = if (isSmallDevice()) 24.dp else 32.dp),
                color = colors.headerTextColor,
                style = TextStyle(fontSize = 12.sp)
            )

            Box(
                Modifier
                    .fillMaxWidth()
                    .paddingFromBaseline(top = if (isSmallDevice()) 0.dp else 64.dp)
            ) {
                Text(
                    text = "$day, $month ${state.selected.dayOfMonth}",
                    modifier = Modifier.align(Alignment.CenterStart),
                    color = colors.headerTextColor,
                    style = TextStyle(fontSize = 30.sp, fontWeight = W400)
                )
            }

            Spacer(Modifier.height(if (isSmallDevice()) 8.dp else 16.dp))
        }
    }
}

private fun PlatformLocalDate.getDates(): Pair<Int, Int> {
    return Pair(getFirstDayOfMonth(), getNumDays())
}
