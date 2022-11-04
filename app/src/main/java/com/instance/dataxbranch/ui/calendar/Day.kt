package com.instance.dataxbranch.ui.calendar

import java.time.LocalDate

interface Day {
    val date: LocalDate
    val isCurrentDay: Boolean
    val isFromCurrentMonth: Boolean
}
