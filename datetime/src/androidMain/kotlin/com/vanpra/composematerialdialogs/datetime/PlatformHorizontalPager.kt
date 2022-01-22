package com.vanpra.composematerialdialogs.datetime

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

// Horizontal Pager

actual typealias PlatformPagerState = PagerState

actual val PlatformPagerState.platformPageCount: Int
    get() = this.pageCount

@Composable
actual fun rememberPlatformPagerState(initialPage: Int) = rememberPagerState(initialPage = initialPage)

@Composable
actual fun PlatformHorizontalPager(
    modifier: Modifier,
    count: Int,
    state: PlatformPagerState,
    verticalAlignment: Alignment.Vertical,
    content: @Composable PlatformPagerScope.(Int) -> Unit,
) = HorizontalPager(
    modifier = modifier,
    count = count,
    state = state,
    verticalAlignment = verticalAlignment,
    content = {
        content(object : PlatformPagerScope {}, this.currentPage)
    }
)