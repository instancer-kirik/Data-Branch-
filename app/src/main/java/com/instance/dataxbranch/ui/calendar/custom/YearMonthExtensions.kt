package com.instance.dataxbranch.ui.calendar.custom

import java.time.YearMonth

internal operator fun YearMonth.dec() = this.minusMonths(1)

internal operator fun YearMonth.inc() = this.plusMonths(1)
