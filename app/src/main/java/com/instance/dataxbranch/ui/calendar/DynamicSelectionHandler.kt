package com.instance.dataxbranch.ui.calendar




import java.time.LocalDate

public object DynamicSelectionHandler {
    public fun calculateNewSelection(
        date: LocalDate,
        selection: List<LocalDate>,
        selectionMode: SelectionMode,
    ): List<LocalDate> = when (selectionMode) {
        SelectionMode.None -> emptyList()
        SelectionMode.Single -> if (date == selection.firstOrNull()) {
            emptyList()
        } else {
            listOf(date)
        }
        SelectionMode.Multiple -> selection.addOrRemoveIfExists(date)
        SelectionMode.Period -> when {
            date.isBefore(selection.startOrMax()) -> listOf(date)
            date.isAfter(selection.startOrMax()) -> selection.fillUpTo(date)
            date == selection.startOrMax() -> emptyList()
            else -> selection
        }
    }
}
