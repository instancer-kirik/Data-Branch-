package com.instance.dataxbranch.ui.calendar.custom

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.instance.dataxbranch.ui.calendar.WeekDay

import java.time.format.TextStyle
import java.util.*

@Immutable
internal data class Week(
    val isFirstWeekOfTheMonth: Boolean = false,
    val days: List<WeekDay>,//was List<Day12>
)

@Composable
fun Default12dWeekHeader(
    daysOfWeek: List<DayOf12dWeek>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight()
            )
        }
    }
}

internal fun <T> Array<T>.rotateRight(n: Int): List<T> = takeLast(n) + dropLast(n)
