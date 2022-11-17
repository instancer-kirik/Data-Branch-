package com.instance.dataxbranch.ui.calendar.custom


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.instance.dataxbranch.data.repository.GeneralRepository
import com.instance.dataxbranch.ui.calendar.*


import com.instance.dataxbranch.ui.calendar.custom.rotateRight


import java.time.LocalDate


import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * State of the calendar composable
 *
 * @property monthState currently showed month
 * @property selectionState handler for the calendar's selection
 *
@Stable
class CalendarState<T : SelectionState>(
    val monthState: MonthState,
    val selectionState: T,
)
*/
/**
 * [Calendar] implementation using a [DynamicSelectionState] as a selection handler.
 *
 *  * Basic usage:
 * ```
 *  @Composable
 *  fun MainScreen() {
 *    SelectableCalendar()
 *  }
 * ```
 *
 * @param modifier
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param today current day, defaults to [LocalDate.now]
 * @param showAdjacentMonths whenever to show or hide the days from adjacent months
 * @param horizontalSwipeEnabled whenever user is able to change the month by horizontal swipe
 * @param calendarState state of the composable
 * @param dayContent composable rendering the current day
 * @param monthHeader header for showing the current month and controls for changing it
 * @param weekHeader header for showing captions for each day of week
 * @param monthContainer container composable for all the days in current month
 */
@Composable
fun SelectableCalendar12(
    modifier: Modifier = Modifier,
    firstDayOfWeek: DayOf12dWeek = getFirstDayOfWeek(),
    today: LocalDate = LocalDate.now(),
    showAdjacentMonths: Boolean = true,
    horizontalSwipeEnabled: Boolean = true,
    calendarState: CalendarState<DynamicSelectionState> = rememberSelectableCalendarState(),
    dayContent: @Composable BoxScope.(DayState<DynamicSelectionState>) -> Unit = { DisplayDay(it) },
    monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
    weekHeader: @Composable BoxScope.(List<DayOf12dWeek>) -> Unit = { Default12dWeekHeader(it) },
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
        Box { content(PaddingValues()) }
    },
    repo: GeneralRepository
) {
    Calendar12(
        modifier = modifier,
        firstDayOfWeek = firstDayOfWeek,
        today = today,
        showAdjacentMonths = showAdjacentMonths,
        horizontalSwipeEnabled = horizontalSwipeEnabled,
        calendarState = calendarState,
        dayContent = dayContent,
        monthHeader = monthHeader,
        weekHeader = weekHeader,
        monthContainer = monthContainer,
        repo = repo
    )
}

/**
 * [Calendar] implementation without any mechanism for the selection.
 *
 * Basic usage:
 * ```
 *  @Composable
 *  fun MainScreen() {
 *    StaticCalendar()
 *  }
 * ```
 *
 * @param modifier
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param today current day, defaults to [LocalDate.now]
 * @param showAdjacentMonths whenever to show or hide the days from adjacent months
 * @param horizontalSwipeEnabled whenever user is able to change the month by horizontal swipe
 * @param calendarState state of the composable
 * @param dayContent composable rendering the current day
 * @param monthHeader header for showing the current month and controls for changing it
 * @param weekHeader header for showing captions for each day of week
 * @param monthContainer container composable for all the days in current month
 */
@Composable
fun StaticCalendar12(

    modifier: Modifier,
    data:Map<LocalDate, List<String>>,
    firstDayOfWeek: DayOf12dWeek = getFirstDayOfWeek(),
    today: LocalDate = LocalDate.now(),
    showAdjacentMonths: Boolean = true,
    horizontalSwipeEnabled: Boolean = true,
    calendarState: CalendarState<EmptySelectionState> = rememberCalendarState(),
    dayContent: @Composable BoxScope.(DayState<EmptySelectionState>) -> Unit = {
        //asdf.date
        //this works like if you've got data in passed map for day, it displays it, else does prime arg val
        //data[asdf.date]?.let { it1 -> DisplayDay(asdf,displayData=it1) }?:run{DisplayDay(asdf)}
        //com.instance.dataxbranch.ui.calendar.DisplayDay(state = it)

        data[it.date]?.let { it1 -> DisplayDay(it,displayData=it1) }?:DisplayDay(it)
    },

    monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
    weekHeader: @Composable BoxScope.(List<DayOf12dWeek>) -> Unit = { Default12dWeekHeader(it) },
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
        Box { content(PaddingValues()) }
    },
    repo: GeneralRepository
) {
    Calendar12(
        modifier = modifier,
        firstDayOfWeek = firstDayOfWeek,
        today = today,
        showAdjacentMonths = showAdjacentMonths,
        horizontalSwipeEnabled = horizontalSwipeEnabled,
        calendarState = calendarState,
        dayContent = dayContent,
        monthHeader = monthHeader,
        weekHeader = weekHeader,
        monthContainer = monthContainer,
        repo = repo
    )
}

/**
 * Composable for showing a calendar. The calendar state has to be provided by the call site. If you
 * want to use built-in implementation, check out:
 * [SelectableCalendar] - calendar composable handling selection that can be changed at runtime
 * [StaticCalendar] - calendar without any mechanism for selection
 *
 * @param modifier
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param today current day, defaults to [LocalDate.now]
 * @param showAdjacentMonths whenever to show or hide the days from adjacent months
 * @param horizontalSwipeEnabled whenever user is able to change the month by horizontal swipe
 * @param calendarState state of the composable
 * @param dayContent composable rendering the current day
 * @param monthHeader header for showing the current month and controls for changing it
 * @param weekHeader header for showing captions for each day of week
 * @param monthContainer container composable for all the days in current month
 */
@Composable
fun <T : SelectionState> Calendar12(
    calendarState: CalendarState<T>,
    modifier: Modifier = Modifier,
    firstDayOfWeek: DayOf12dWeek = getFirstDayOfWeek(),
    today: LocalDate = LocalDate.now(),
    showAdjacentMonths: Boolean = true,
    horizontalSwipeEnabled: Boolean = true,
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit = { DisplayDay(it) },
    monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
    weekHeader: @Composable BoxScope.(List<DayOf12dWeek>) -> Unit = { Default12dWeekHeader(it) },
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
        Box { content(PaddingValues()) }
    },
    repo:GeneralRepository
) {

    val daysOfWeek = remember(firstDayOfWeek) {
       DayOf12dWeek.values().rotateRight(DaysOfWeek- firstDayOfWeek.ordinal)
    }

    Column(
        modifier = modifier,
    ) {
        monthHeader(calendarState.monthState)
        if (horizontalSwipeEnabled) {
            CustomMonthPager(
                showAdjacentMonths = showAdjacentMonths,
                monthState = calendarState.monthState,
                selectionState = calendarState.selectionState,
                today = today,
                daysOfWeek = daysOfWeek,
                dayContent = dayContent,
                weekHeader = weekHeader,
                monthContainer = monthContainer,
                repo = repo
            )
        } else {
            Month12Content(
                currentMonth = calendarState.monthState.currentMonth,
                showAdjacentMonths = showAdjacentMonths,
                selectionState = calendarState.selectionState,
                today = today,
                daysOfWeek = daysOfWeek,
                dayContent = dayContent,
                weekHeader = weekHeader,
                monthContainer = monthContainer,
                repo = repo
            )
        }
    }
}

fun getFirstDayOfWeek() = DayOf12dWeek.A


fun standardGetFirstDayOfWeek()=WeekFields.of(Locale.getDefault()).firstDayOfWeek
/**
 * Helper function for providing a [CalendarState] implementation with selection mechanism.
 *
 * @param initialMonth initially rendered month
 * @param initialSelection initial selection of the composable
 * @param initialSelectionMode initial mode of the selection
 * @param confirmSelectionChange callback for optional side-effects handling and vetoing the state change
 */
@Composable
fun rememberSelectableCalendarState(
    initialMonth: YearMonth = YearMonth.now(),
    initialSelection: List<LocalDate> = emptyList(),
    initialSelectionMode: SelectionMode = SelectionMode.Single,
    confirmSelectionChange: (newValue: List<LocalDate>) -> Boolean = { true },
    monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
        MonthState(initialMonth = initialMonth)
    },
    selectionState: DynamicSelectionState = rememberSaveable(
        saver = DynamicSelectionState.Saver(confirmSelectionChange),
    ) {
        DynamicSelectionState(confirmSelectionChange, initialSelection, initialSelectionMode)
    },
): CalendarState<DynamicSelectionState> = remember { CalendarState(monthState, selectionState) }

/**
 * Helper function for providing a [CalendarState] implementation without a selection mechanism.
 *
 * @param initialMonth initially rendered month
 */
@Composable
fun rememberCalendarState(
    initialMonth: YearMonth = YearMonth.now(),
    monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
        MonthState(initialMonth = initialMonth)
    },
): CalendarState<EmptySelectionState> = remember { CalendarState(monthState, EmptySelectionState) }
