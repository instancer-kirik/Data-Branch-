/*
package com.instance.dataxbranch.ui.calendar.custom

import java.time.LocalDate


object DynamicSelectionHandler12 {
    fun calculateNewSelection(
        date: LocalDate,
        selection: List<LocalDate>,
        selectionMode12: SelectionMode12,
    ): List<LocalDate> = when (selectionMode12) {
        SelectionMode12.None -> emptyList()
        SelectionMode12.Single -> if (date == selection.firstOrNull()) {
            emptyList()
        } else {
            listOf(date)
        }
        SelectionMode12.Multiple -> selection.addOrRemoveIfExists(date)
        SelectionMode12.Period -> when {
            selection.startOrMax()?.let { date.isBefore(it) } == true -> listOf(date)
            selection.startOrMax()?.let { date.isAfter(it) } == true -> selection.fillUpTo(date)
            date == selection.startOrMax() -> emptyList()
            else -> selection
        }
    }
}
*/
