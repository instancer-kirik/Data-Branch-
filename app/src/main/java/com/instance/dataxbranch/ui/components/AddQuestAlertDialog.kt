package com.instance.dataxbranch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants.ADD
import com.instance.dataxbranch.core.Constants.ADD_QUEST
import com.instance.dataxbranch.core.Constants.DESC
import com.instance.dataxbranch.core.Constants.DISMISS
import com.instance.dataxbranch.core.Constants.QUEST_TITLE
import com.instance.dataxbranch.ui.viewModels.QuestsViewModel
import kotlinx.coroutines.job


@Composable
fun AddQuestAlertDialog(
    viewModel: QuestsViewModel = hiltViewModel(),
    primertitle: String = "",
    primerdesc: String = "",
    openState: MutableState<Boolean> = viewModel.openDialogState

) {
    var title by remember { mutableStateOf(primertitle) }
    var description by remember { mutableStateOf(primerdesc) }
    val focusRequester = FocusRequester()

    if (openState.value) {
        AlertDialog(
            onDismissRequest = {
                openState.value = false
            },
            title = {
                Text(
                    text = ADD_QUEST
                )
            },
            text = {
                Column {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = {
                            Text(
                                text = QUEST_TITLE
                            )
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                    LaunchedEffect(Unit) {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = {
                            Text(
                                text = DESC
                            )
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openState.value = false
                        viewModel.addQuest(title, description,"author")
                    }
                ) {
                    Text(
                        text = ADD
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openState.value = false
                    }
                ) {
                    Text(
                        text = DISMISS
                    )
                }
            }
        )
    }
}