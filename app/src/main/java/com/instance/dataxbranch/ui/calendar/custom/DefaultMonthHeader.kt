package com.instance.dataxbranch.ui.calendar.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.instance.dataxbranch.ui.calendar.MonthState

import java.time.format.TextStyle
import java.util.*

@Composable
fun DefaultMonthHeader(
    monthState: MonthState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier.testTag("Decrement"),
            onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) }
        ) {
            Image(
                imageVector = Icons.Default.KeyboardArrowLeft,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                contentDescription = "Previous",
            )
        }
        Text(
            modifier = Modifier.testTag("MonthLabel"),
            text = monthState.currentMonth.month
                .getDisplayName(TextStyle.FULL, Locale.getDefault())
                .lowercase()
                .replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.h4,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = monthState.currentMonth.year.toString(), style = MaterialTheme.typography.h4)
        IconButton(
            modifier = Modifier.testTag("Increment"),
            onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }
        ) {
            Image(
                imageVector = Icons.Default.KeyboardArrowRight,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                contentDescription = "Next",
            )
        }
    }
}