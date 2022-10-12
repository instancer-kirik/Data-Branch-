package com.instance.dataxbranch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@Composable
fun AddItemEntityAlertDialog(

    viewModel: UserViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }//viewModel.selectedAE?.name ?:""
    //var description by remember { mutableStateOf("") }
    //var author by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()

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
                    TextField(
                        value = name,
                        onValueChange = { name = it },
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


                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.addItemOnClick(name)

                        viewModel.characterDialogState.value = false
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
                        viewModel.characterDialogState.value = false
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