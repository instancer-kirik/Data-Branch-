package com.instance.dataxbranch.ui.calendar.custom

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.instance.dataxbranch.ui.calendar.DayState
import com.instance.dataxbranch.ui.calendar.SelectionState


@Composable
internal fun <T : SelectionState> WeekContent(
    week: Week,
    selectionState: T,
    modifier: Modifier = Modifier,
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = if (week.isFirstWeekOfTheMonth) Arrangement.End else Arrangement.Start
    ) {
        week.days.forEachIndexed { index, day ->
            Box(
                modifier = Modifier.fillMaxWidth(1f / (DaysOfWeek- index))
            ) {
                dayContent(DayState(day, selectionState))
            }
        }
    }
}
