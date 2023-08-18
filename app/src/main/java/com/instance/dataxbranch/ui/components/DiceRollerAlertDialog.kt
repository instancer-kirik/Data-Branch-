package com.instance.dataxbranch.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.data.EventType
import com.instance.dataxbranch.ui.viewModels.QuestsViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import kotlinx.coroutines.job
import kotlin.random.Random

@Composable
fun DiceRollerAlertDialog (

    viewModel: UserViewModel = hiltViewModel(),
    onDone: (String, String, /*EventType*/) -> Unit
) {
    val focusRequester = FocusRequester()
    var diceResults by remember { mutableStateOf(mapOf<String, List<Int>>()) }

    var diceCounters by remember { mutableStateOf(mapOf("d4" to 0, "d6" to 0, "d8" to 0, "d10" to 0, "d12" to 0, "d20" to 0)) }
    if (viewModel.characterDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.characterDialogState.value = false
            },
            title = {
                Text(
                    text = Constants.ADD_ITEM
                )
            },
            text = {
                Column {
                    LazyColumn {

                        items(diceCounters.keys.toList()) { diceType ->
                            DiceCounterItem(
                                diceType = diceType,
                                counterValue = diceCounters[diceType] ?: 0,
                                onCounterValueChanged = { newValue ->
                                    diceCounters = diceCounters.toMutableMap().apply {
                                        this[diceType] = newValue
                                    }
                                },
                                rolledValues = diceResults[diceType] ?: emptyList()
                            )

                            // modifier = Modifier.focusRequester(focusRequester)

                        }


                    }
                    LaunchedEffect(Unit) {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    }
                }
            },



            confirmButton = {
                TextButton(
                    onClick = {
                        //roll dice
                        //store dice results in db
                        rollDice(diceCounters)?.let { results ->
                            diceResults = results
                            viewModel.diceDialogState.value = false
                        }
                    }
                ) {
                    Text(
                        text = Constants.ADD
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.diceDialogState.value = false
                    }
                ) {
                    Text(
                        text = Constants.DISMISS
                    )
                }
            }
        )
    }
}
    private fun rollDice(diceCounters: Map<String, Int>): Map<String, List<Int>>? {
        val random = Random.Default
        val results = mutableMapOf<String, List<Int>>()

        for ((diceType, count) in diceCounters) {
            if (count > 0) {
                val rolls = List(count) { random.nextInt(1, diceType.substring(1).toInt() + 1) }
                results[diceType] = rolls
            }
        }

        return if (results.isNotEmpty()) results else null
    }

@Composable
fun DiceCounterItem(
    diceType: String,
    counterValue: Int,
    onCounterValueChanged: (Int) -> Unit,
    rolledValues: List<Int>
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$diceType:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = { if (counterValue > 0) onCounterValueChanged(counterValue - 1) }
            ) {
                Text(text = "-")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = counterValue.toString())
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onCounterValueChanged(counterValue + 1) }
            ) {
                Text(text = "+")
            }
            Text(
                text = rolledValues.joinToString(", "),
                fontSize = 16.sp
            )
        }
    }
}