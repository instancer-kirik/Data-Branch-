package com.instance.dataxbranch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.destinations.*
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*
@Composable
fun TodayPopupAlertDialog(

    //QviewModel: QuestsViewModel = hiltViewModel(),
    UviewModel: UserViewModel = hiltViewModel(),
    //viewModel: DevViewModel= hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context= LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()
    val me = UviewModel.getMeWithAbilities()
    if (UviewModel.termsDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                UviewModel.termsDialogState.value = false
            },
            title = {
                Text(
                    text = "      BETA\n"
                            +Constants.TERMS+ " and Stuff"

                )
            },
            text = {
                Column {
                    Button(onClick = { showToast(context,"boop")}, modifier=Modifier.padding(2.dp)){Text("BOOP")}
                    Text("TODAYAYYAYAYAYYYYY")


                    /* TextField(
                         value = title,
                         onValueChange = { title = it },
                         placeholder = {
                             Text(
                                 text = Constants.TERMS
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
                                 text = Constants.DESC
                             )
                         }
                     )*/
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        UviewModel.termsDialogState.value = false
                        me.user.terms_status = Calendar.getInstance().time.toString()
                        UviewModel.singleConditionsDialog.value = false
                    }
                ) {
                    Text(
                        text = Constants.OK
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        UviewModel.termsDialogState.value = false
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
/*@Composable
fun MainContent() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("GFG | WebView", color = Color.White) }, backgroundColor = Color(0xff0f9d58)) },
        content = { MyContent() }
    )
}*/

// Creating a composable
// function to create WebView
// Calling this function as
// content in the above function

// For displaying preview in
// the Android Studio IDE emulator
/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainContent()
}*/
