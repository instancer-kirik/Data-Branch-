package com.instance.dataxbranch.ui.calendar.custom

import java.time.DayOfWeek
import java.time.LocalDate


internal fun Collection<LocalDate>.addOrRemoveIfExists(date: LocalDate) =
    if (contains(date)) {
        this - date
    } else {
        this + date
    }

internal infix fun DayOfWeek.daysUntil(other: DayOf12dWeek) = (DaysInAWeek + (value - other.value)) % DaysInAWeek