package com.instance.dataxbranch.ui.calendar

import java.time.LocalDate

public interface Day {
    public val date: LocalDate
    public val isCurrentDay: Boolean
    public val isFromCurrentMonth: Boolean
}
