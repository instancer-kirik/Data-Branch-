package com.instance.dataxbranch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.ui.AbilityDetailScreen
import com.instance.dataxbranch.destinations.*
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.job

@Composable
fun EditAbilityEntityAlertDialog(
    viewModel: UserViewModel = hiltViewModel(),
navigator: DestinationsNavigator
) {
    var desc by remember { mutableStateOf(viewModel.selectedAE.desc ?:"") }
    var inloadout by remember { mutableStateOf(viewModel.selectedAE.inloadout) }





    //var description by remember { mutableStateOf("") }
    //var author by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()

    if (viewModel.openDialogState2.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.openDialogState2.value = false
            },
            title = {
                Text(
                    text = Constants.EDIT_ABILITY
                )
            },
            text = {
                Column {
                    Text(viewModel.selectedAE.title +"")
                    TextField(
                        value = desc,
                        onValueChange = { desc = it },
                        placeholder = {
                            Text(
                                text ="notes"
                            )
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                    Button(onClick ={
                        viewModel.generalRepository.selectedAE=viewModel.selectedAE
                        navigator.navigate(AbilityDetailScreenDestination)},content = {Text("More")})

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
                        viewModel.selectedAE.desc = desc
                        viewModel.selectedAE.let { viewModel.update(it) }
                        viewModel.openDialogState2.value = false

                    }
                ) {
                    Text(
                        text = Constants.SAVE
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.openDialogState2.value = false
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