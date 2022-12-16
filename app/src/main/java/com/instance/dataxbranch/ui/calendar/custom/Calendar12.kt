package com.instance.dataxbranch.ui.calendar.custom


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.DayStatus
import com.instance.dataxbranch.data.EntityType
import com.instance.dataxbranch.data.repository.GeneralRepository
import com.instance.dataxbranch.domain.getNow
import com.instance.dataxbranch.domain.parse
import com.instance.dataxbranch.quests.QuestWithObjectives

import com.instance.dataxbranch.ui.calendar.*
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.*
import java.time.format.DateTimeFormatter


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
    dayContent: @Composable BoxScope.(DayState<DynamicSelectionState>) -> Unit = { DisplayDay(it,onClick={day->
        Log.d("day12",day.toString())

    }) },
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
    data:Map<LocalDate, DayData>,
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

        data[it.date]?.let { it1 -> DisplayDay(it,displayData=it1,onClick={day,p2->
            Log.d("day12WithData",day.toString())

        }) }?:DisplayDay(it,onClick={day->
            Log.d("day12default",day.toString())

        })
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
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit = { DisplayDay(it,onClick={day->
        Log.d("day12BASE",day.toString())//this doesn't get called, specific ones do

    }) },
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
@Composable
fun StaticCalendarForBottomSheet12(

    modifier: Modifier,
    onClick: (LocalDate,DayData) -> Unit= { _, _ -> },
    data:Map<LocalDate, DayData>,
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

        data[it.date]?.let { it1 -> DisplayDay(it,displayData=it1,onClick=onClick) }?:DisplayDay(it,onClick=onClick)
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


class Event(
    var start: LocalDateTime?=null,
    var end: LocalDateTime?=null,
    var text: String="",//title
    var description: String="",
    var location: String="",
    var timeZone: ZoneId=ZoneId.systemDefault(),
    //val title: String = "",
    /*val startDate: String = "",
    val endDate: String = "",*/
    //val id: String = "",
    var timestamp: String = "",
    /*data class Event()*/
    val uuid:String="", var type:Enum<EntityType> = EntityType.NONE, //val text:String=""

) {
    constructor(questWithObjectives: QuestWithObjectives) : this(
        start = parse(questWithObjectives.quest.targetDateTime),
        end = parse(questWithObjectives.quest.targetDateTime),
        text = "Q ${questWithObjectives.quest.title}",
        description = questWithObjectives.quest.describe(),
        uuid = questWithObjectives.quest.uuid,
        type = EntityType.QUEST
    )
    private fun stampTime(){
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = now.format(formatter)
        timestamp=formatted
    }
    private fun updateDates(){
        if (start==null) start=LocalDateTime.now()
        if (end==null) end= LocalDateTime.now().plusDays(10)
    }
    private fun toIcsString(): String {
        updateDates()
        stampTime()
        val startZoned = ZonedDateTime.of(start, timeZone)
        val endZoned = ZonedDateTime.of(end, timeZone)

        // Use the ICalendar format to create the string representation of the event
        // See https://en.wikipedia.org/wiki/ICalendar for more information
        return """
            BEGIN:VCALENDAR
            BEGIN:VEVENT
            UID:${uuid}
            DTSTAMP:${timestamp}
            DTSTART;TZID=${timeZone.getId()}:${startZoned.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))}
            DTEND;TZID=${timeZone.getId()}:${endZoned.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))}
            SUMMARY:$text
            DESCRIPTION:$description
            LOCATION:$location
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()
    }
    fun downloadFile(url: String, fileName: String) {
        val url = URL(url)
        val con = url.openConnection() as HttpURLConnection
        val inputStream = con.inputStream
        val fileOutputStream = FileOutputStream(fileName)
        inputStream.use { input ->
            fileOutputStream.use { fileOut ->
                input.copyTo(fileOut)
            }
        }
    }
    fun generateAndDownloadIcsFile(event: Event=this, fileName: String) {
        // Generate the .ics file as a string
        val icsFile = event.toIcsString()
        // Create a temporary file with the specified file name
        val tempFile = File.createTempFile(fileName, ".ics")
        // Write the .ics file contents to the temporary file
        tempFile.writeText(icsFile)
        // Download the temporary file
        downloadFile(tempFile.toURI().toURL().toString(), fileName)
//         Delete the temporary file
        tempFile.delete()
    }

    fun test(){
        val event = Event(
            text = "Custom Quest",
            location = "The Adventurer's Guild",
            start = LocalDateTime.of(2022, 12, 15, 10, 0),
            end = LocalDateTime.of(2022, 12, 15, 12, 0),
            description = "Join us for an exciting custom quest at the Adventurer's Guild!"
        )

        val icsString = event.toIcsString()

        generateAndDownloadIcsFile (event, "event.ics")
    }
    /*fun generateIcsFile(event: Event): String {
        val builder = StringBuilder()

        // Add the file header
        builder.append("BEGIN:VCALENDAR\n")
        builder.append("VERSION:2.0\n")
        builder.append("PRODID:-//Your Company//Your Product//EN\n")
        builder.append("CALSCALE:GREGORIAN\n")          // Add the event details
        builder.append("BEGIN:VEVENT\n")
        builder.append("UID:${event.id}\n")
        builder.append("DTSTAMP:${event.timestamp}\n")
        builder.append("DTSTART:${event.startDate}\n")
        builder.append("DTEND:${event.endDate}\n")
        builder.append("SUMMARY:${event.summary}\n")
        builder.append("LOCATION:${event.location}\n")
        builder.append("DESCRIPTION:${event.description}\n")
        builder.append("END:VEVENT\n")
        // Add the file footer
        builder.append("END:VCALENDAR\n")
        return builder.toString()
    }*/
}
fun parseEvent(icsFile: String): Event {//Untested
    val event = Event()
    val lines = icsFile.split("\n")

    var inEvent = false
    for (line in lines) {
        if (line.startsWith("BEGIN:VEVENT")) {
            inEvent = true
        } else if (line.startsWith("END:VEVENT")) {
            inEvent = false
        } else if (inEvent) {
            if (line.startsWith("SUMMARY:")) {
                event.text = line.substring(8)
            } else if (line.startsWith("LOCATION:")) {
                event.location = line.substring(9)
            } else if (line.startsWith("DTSTART:")) {
                event.start = parse(line.substring(8))
            } else if (line.startsWith("DTEND:")) {
                event.end = parse(line.substring(6))
            }
        }
    }
    Log.d(TAG, "Event: $event")
    return event
}
class DayData( var color: Color = Color.Transparent, var status:Enum<DayStatus> = DayStatus.NONE, var events:List<Event> =listOf()) {//var date:LocalDate = LocalDate.now(),)
fun addEvent(event:Event){
    events=events.plus(event)
}
fun eventsOnDay():String{
    var str=""
    events.forEach {
        str+=it.text
    }
    return str
}
    //defaults newStatus to old status, gets overwrote
    fun copy(newStatus:Enum<DayStatus> = status, newColor: Color = color, eventList:List<Event> =events): DayData {//status = DayStatus.fromStringOrDefault(it.value.first)) ?: DayData(color = Color.White,  DayStatus.fromStringOrDefault(it.value.first), listOf())
        val newDayData = DayData()
        newDayData.color = newColor
        newDayData.status = newStatus
        newDayData.events = eventList
        return newDayData
    }
    /*fun copy(status: DayStatus): DayData? {
        return DayData(color, status, events)
    }*/

}