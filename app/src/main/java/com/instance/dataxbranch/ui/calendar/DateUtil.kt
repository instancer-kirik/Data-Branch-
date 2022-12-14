package com.instance.dataxbranch.ui.calendar

import com.instance.dataxbranch.domain.parse
import java.time.DayOfWeek
import java.time.LocalDate


internal fun Collection<LocalDate>.addOrRemoveIfExists(date: LocalDate) =
    if (contains(date)) {
        this - date
    } else {
        this + date
    }

internal infix fun DayOfWeek.daysUntil(other: DayOfWeek) = (7 + (value - other.value)) % 7

fun find(){
    parse("2022-11-05T16:24:30.284431")
}