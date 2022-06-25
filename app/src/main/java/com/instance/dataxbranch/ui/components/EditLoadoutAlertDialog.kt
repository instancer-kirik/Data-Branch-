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
import kotlinx.coroutines.job


    @Composable
    fun EditLoadoutAlertDialog(
        viewModel: UserViewModel = hiltViewModel()
    ) {

       // var title by remember { mutableStateOf(viewModel.selectedAE?.title ?:"") }
        //var description by remember { mutableStateOf("") }
        //var author by remember { mutableStateOf("") }
        val focusRequester = FocusRequester()

        if (viewModel.openDialogState3.value) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.openDialogState3.value = false
                },
                title = {
                    Text(
                        text = Constants.EDIT_ABILITY
                    )
                },
                text = {
                    Column {
viewModel.generalRepository.getMe().abilities.forEach { ae->Text("ABILITY: "+ae.title)
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
                            viewModel.openDialogState3.value = false
                            //viewModel.addNewAbilityEntity(title)
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
                            viewModel.openDialogState3.value = false
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