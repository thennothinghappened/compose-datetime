package com.vanpra.composematerialdialogs.datetime

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Horizontal Pager For Date Picker

expect class PlatformPagerState {
    var currentPage: Int
    suspend fun scrollToPage(page: Int, pageOffset: Float = 0f)
    suspend fun animateScrollToPage(page: Int, pageOffset: Float = 0f)
}

expect val PlatformPagerState.platformPageCount: Int

interface PlatformPagerScope

@Composable
expect fun rememberPlatformPagerState(initialPage: Int = 0): PlatformPagerState

@Composable
expect fun PlatformHorizontalPager(
    modifier: Modifier,
    count: Int,
    state: PlatformPagerState,
    verticalAlignment: Alignment.Vertical,
    content: @Composable PlatformPagerScope.(Int) -> Unit
)