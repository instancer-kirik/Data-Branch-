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
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.ui.viewModels.UserViewModel

import kotlinx.coroutines.job

@Composable
fun AddAbilityEntityAlertDialog(

    viewModel: UserViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }//viewModel.selectedAE?.title ?:""
    //var description by remember { mutableStateOf("") }
    //var author by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()

    if (viewModel.openDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.openDialogState.value = false
            },
            title = {
                Text(
                    text = Constants.ADD_ABILITY
                )
            },
            text = {
                Column {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
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
                        viewModel.openDialogState.value = false
                        viewModel.addNewAbilityEntity(title)
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
                        viewModel.openDialogState.value = false
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