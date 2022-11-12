/*
package com.instance.dataxbranch.ui.calendar.custom

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import java.time.LocalDate


@Stable
interface Selection12State {
    fun isDateSelected(date: LocalDate): Boolean = false
    fun onDateSelected(date: LocalDate) { }
}

*/
/**
 * Class that enables for dynamically changing selection modes in the runtime. Depending on the mode, selection changes differently.
 * Mode can be varied by setting desired [SelectionMode12] in the [selectionMode12] mutable property.
 * @param confirmSelectionChange return false from this callback to veto the selection change
 *//*

@Stable
class DynamicSelection12State(
    private val confirmSelectionChange: (newValue: List<LocalDate>) -> Boolean = { true },
    selection: List<LocalDate>,
    selectionMode12: SelectionMode12,
) : Selection12State {

    private var _selection by mutableStateOf(selection)
    private var _selectionMode by mutableStateOf(selectionMode12)

    var selection: List<LocalDate>
        get() = _selection
        set(value) {
            if (value != selection && confirmSelectionChange(value)) {
                _selection = value
            }
        }

    var selectionMode12: SelectionMode12
        get() = _selectionMode
        set(value) {
            if (value != selectionMode12) {
                _selection = emptyList()
                _selectionMode = value
            }
        }

    override fun isDateSelected(date: LocalDate): Boolean = selection.contains(date)

    override fun onDateSelected(date: LocalDate) {
        selection = DynamicSelectionHandler12.calculateNewSelection(date, selection, selectionMode12)
    }

    internal companion object {
        @Suppress("FunctionName", "UNCHECKED_CAST") // Factory function
        fun Saver(
            confirmSelectionChange: (newValue: List<LocalDate>) -> Boolean,
        ): Saver<DynamicSelection12State, Any> =
            listSaver(
                save = { raw ->
                    listOf(raw.selectionMode12, raw.selection.map { it.toString() })
                },
                restore = { restored ->
                    DynamicSelection12State(
                        confirmSelectionChange = confirmSelectionChange,
                        selectionMode12 = restored[0] as SelectionMode12,
                        selection = (restored[1] as? List<String>)?.map { LocalDate.parse(it) }.orEmpty(),
                    )
                }
            )
    }
}

@Immutable
object EmptySelection12State : Selection12State {
    override fun isDateSelected(date: LocalDate): Boolean = false

    override fun onDateSelected(date: LocalDate): Unit = Unit
}
*/
