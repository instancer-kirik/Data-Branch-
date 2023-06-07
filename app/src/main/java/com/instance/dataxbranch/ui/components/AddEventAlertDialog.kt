package com.instance.dataxbranch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.data.EventType
import com.instance.dataxbranch.ui.viewModels.QuestsViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@Composable
fun AddEventAlertDialog(
    qViewModel: QuestsViewModel = hiltViewModel(),
    primermain: String = "",
    primerdesc: String = "",
    viewModel: UserViewModel = hiltViewModel(),
    onDone: (String, String, EventType) -> Unit
) {

    var maintext by remember { mutableStateOf(primermain) }
    var description by remember { mutableStateOf(primerdesc) }
    val focusRequester = FocusRequester()
    var type by remember { mutableStateOf(EventType.DEFAULT) }
    var openTypePicker by remember { mutableStateOf(false) }

    if (viewModel.newEventDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.newEventDialogState.value = false
            },
            title = {
                Text(
                    text = Constants.ADD_EVENT
                )
            },
            text = {

                Column {
                    TextField(
                        value = maintext,
                        onValueChange = { maintext = it },
                        placeholder = {
                            Text(
                                text = Constants.QUEST_TITLE
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
                        modifier = Modifier.height(8.dp)
                    )
                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = {
                            Text(
                                text = Constants.DESC
                            )
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    OutlinedButton(
                        onClick = {
                            openTypePicker=!openTypePicker
                        }
                    ) {

                    if(openTypePicker){
                    EntityTypeSpinner(EventType.getList(), selectedOption1 = type.toString(), onDone = {
                        type = EventType.fromStringOrDefault(it)
                        openTypePicker = false
                    })


                }else {
                        Text(text = "Type: ${type.name}")
                    }}}

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.newEventDialogState.value = false
                        qViewModel.viewModelScope.launch { qViewModel.addQuest(maintext, description,"author") }

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
                        viewModel.newEventDialogState.value = false
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

