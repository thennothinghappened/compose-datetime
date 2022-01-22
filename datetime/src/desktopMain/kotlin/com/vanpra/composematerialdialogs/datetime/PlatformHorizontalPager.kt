package com.vanpra.composematerialdialogs.datetime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.sun.jdi.connect.LaunchingConnector

@Composable
actual fun rememberPlatformPagerState(initialPage: Int): PlatformPagerState {
    val listState = rememberLazyListState(initialPage)
    return remember {
        PlatformPagerState(listState)
    }
}

@Composable
actual fun PlatformHorizontalPager(
    modifier: Modifier,
    count: Int,
    state: PlatformPagerState,
    verticalAlignment: Alignment.Vertical,
    content: @Composable PlatformPagerScope.(Int) -> Unit,
) {
    state.internalPageCount = count

    val listState = rememberLazyListState()
    val isVertical = false
    val reverseLayout = false
    val contentPadding = PaddingValues(0.dp)
    val horizontalAlignment = Alignment.CenterHorizontally
    val itemSpacing = 0.dp
    val scope = object : PlatformPagerScope{

    }

    // We only consume nested flings in the main-axis, allowing cross-axis flings to propagate
    // as normal
    val consumeFlingNestedScrollConnection = ConsumeFlingNestedScrollConnection(
        consumeHorizontal = !isVertical,
        consumeVertical = isVertical,
    )

    LazyRow(
        state = listState,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = Arrangement.spacedBy(itemSpacing, horizontalAlignment),
        reverseLayout = reverseLayout,
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        items(
            count = count,
            key = null,
        ) { page ->
            Box(
                Modifier
                    // We don't any nested flings to continue in the pager, so we add a
                    // connection which consumes them.
                    // See: https://github.com/google/accompanist/issues/347
                    .nestedScroll(connection = consumeFlingNestedScrollConnection)
                    // Constraint the content width to be <= than the width of the pager.
                    .fillParentMaxWidth()
                    .wrapContentSize()
            ) {
                content(scope,page)
            }
        }
    }
    LazyRow(modifier = modifier, verticalAlignment = verticalAlignment) {
        items(count) { index ->
            Box(Modifier.fillMaxWidth()) {
                content(object : PlatformPagerScope {}, index)
            }
        }
    }
}


private class ConsumeFlingNestedScrollConnection(
    private val consumeHorizontal: Boolean,
    private val consumeVertical: Boolean,
) : NestedScrollConnection {
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = when (source) {
        // We can consume all resting fling scrolls so that they don't propagate up to the
        // Pager
        NestedScrollSource.Fling -> available.consume(consumeHorizontal, consumeVertical)
        else -> Offset.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        // We can consume all post fling velocity on the main-axis
        // so that it doesn't propagate up to the Pager
        return available.consume(consumeHorizontal, consumeVertical)
    }
}

private fun Offset.consume(
    consumeHorizontal: Boolean,
    consumeVertical: Boolean,
): Offset = Offset(
    x = if (consumeHorizontal) this.x else 0f,
    y = if (consumeVertical) this.y else 0f,
)

private fun Velocity.consume(
    consumeHorizontal: Boolean,
    consumeVertical: Boolean,
): Velocity = Velocity(
    x = if (consumeHorizontal) this.x else 0f,
    y = if (consumeVertical) this.y else 0f,
)