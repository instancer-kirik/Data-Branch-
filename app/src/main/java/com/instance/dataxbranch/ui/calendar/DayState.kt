package com.instance.dataxbranch.ui.calendar

import androidx.compose.runtime.Stable


@Stable
public data class DayState<T : SelectionState>(
    private val day: Day,
    val selectionState: T,
) : Day by day
