package com.instance.dataxbranch.ui.calendar.custom

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.instance.dataxbranch.data.repository.GeneralRepository
import com.instance.dataxbranch.ui.calendar.DayState
import com.instance.dataxbranch.ui.calendar.MonthState
import com.instance.dataxbranch.ui.calendar.SelectionState
import java.time.LocalDate


import java.time.YearMonth


var DaysOfWeek = DayOf12dWeek.values().size

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun <T : SelectionState> CustomMonthPager(
    showAdjacentMonths: Boolean,
    selectionState: T,
    monthState: MonthState,
    daysOfWeek: List<DayOf12dWeek>,
    today: LocalDate,
    modifier: Modifier = Modifier,
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
    weekHeader: @Composable BoxScope.(List<DayOf12dWeek>) -> Unit,
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
    repo: GeneralRepository
) {
    val startIndex = PagerItemCount / 2
    val pagerState = rememberPagerState(initialPage = startIndex)
    val coroutineScope = rememberCoroutineScope()

    val monthPagerState = remember {
        MonthPagerState(
            coroutineScope = coroutineScope,
            monthState = monthState,
            pagerState = pagerState,
        )
    }

    HorizontalPager(
        count = PagerItemCount,
        modifier = modifier.testTag("MonthPager"),
        state = pagerState,
        verticalAlignment = Alignment.Top,
    ) { index ->
        Month12Content(
            showAdjacentMonths = showAdjacentMonths,
            selectionState = selectionState,
            currentMonth = monthPagerState.getMonthForPage(index.toIndex()),
            today = today,
            daysOfWeek = daysOfWeek,
            dayContent = dayContent,
            weekHeader = weekHeader,
            monthContainer = monthContainer,
        repo = repo
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun <T : SelectionState> Month12Content(
    showAdjacentMonths: Boolean,
    selectionState: T,
    currentMonth: YearMonth,
    daysOfWeek: List<DayOf12dWeek>,
    today: LocalDate,
    modifier: Modifier = Modifier,
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
    weekHeader: @Composable BoxScope.(List<DayOf12dWeek>) -> Unit,
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
    repo: GeneralRepository
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            content = { weekHeader(daysOfWeek) },
        )

        monthContainer { paddingValues ->
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(paddingValues)
            ) {
                currentMonth.getWeeks(
                    includeAdjacentMonths = showAdjacentMonths,
                    firstDayOfTheWeek = daysOfWeek.first(),
                    today = today,
                    repo = repo
                ).forEach { week ->
                    WeekContent(
                        week = week,
                        selectionState = selectionState,
                        dayContent = dayContent,
                    )
                }
            }
        }
    }
}
