package com.instance.dataxbranch.ui.calendar.custom





import com.instance.dataxbranch.ui.calendar.WeekDay
import java.time.LocalDate
import java.time.YearMonth


var DaysInAWeek = 12

internal fun YearMonth.getWeeks(
    includeAdjacentMonths: Boolean,
    firstDayOfTheWeek: DayOf12dWeek,
    today: LocalDate = LocalDate.now(),
): List<Week> {
    val daysLength = lengthOfMonth()

    val starOffset = atDay(1).dayOfWeek daysUntil firstDayOfTheWeek
    val endOffset =
        DaysInAWeek - (atDay(daysLength).dayOfWeek daysUntil firstDayOfTheWeek) - 1

    return (1 - starOffset..daysLength + endOffset).chunked(DaysInAWeek).mapIndexed { index, days ->
        Week(
            isFirstWeekOfTheMonth = index == 0,
            days = days.mapNotNull { dayOfMonth ->
                val (date, isFromCurrentMonth) = when (dayOfMonth) {
                    in Int.MIN_VALUE..0 -> if (includeAdjacentMonths) {
                        val previousMonth = this.minusMonths(1)
                        previousMonth.atDay(previousMonth.lengthOfMonth() + dayOfMonth) to false
                    } else {
                        return@mapNotNull null
                    }
                    in 1..daysLength -> atDay(dayOfMonth) to true
                    else -> if (includeAdjacentMonths) {
                        val previousMonth = this.plusMonths(1)
                        previousMonth.atDay(dayOfMonth - daysLength) to false
                    } else {
                        return@mapNotNull null
                    }
                }
 WeekDay(
                    date = date,
                    isFromCurrentMonth = isFromCurrentMonth,
                    isCurrentDay = date.equals(today),
                )
               /* LocalDate.toThis(date)?.let {
                    WeekDay12(
                        date = it,
                        isFromCurrentMonth = isFromCurrentMonth,
                        isCurrentDay = date.equals(today),
                    )
                }*/
            }
        )
    }
}
// fun YearMonth.atDay(dayOfMonth: Int): LocalDate? {
//    throw RuntimeException("Stub!")
//}
// internal infix fun DayOfWeek.daysUntil(other: DayOfWeek) = (7 + (value - other.value)) % 7
//private infix fun java.time.DayOfWeek.daysUntil(other: DayOfWeek)= (7 + (value - other.value)) % 7
///IF i want to have 12 day weeks, need to make:
//fun from7to12(DayOfWeek)=
//fun from12to7(DayOfWeek)=

