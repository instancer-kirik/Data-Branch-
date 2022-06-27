package com.instance.dataxbranch.ui.components

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.Navigator
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.ui.MyContent
import com.instance.dataxbranch.ui.destinations.AbilitiesScreenDestination
import com.instance.dataxbranch.ui.destinations.WebviewScreenDestination
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.QuestsViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.job
import java.time.Instant.now
import java.time.LocalDateTime.now
import java.time.OffsetDateTime.now
import java.util.*


@Composable
fun TermsPopupAlertDialog (
    //QviewModel: QuestsViewModel = hiltViewModel(),
    UviewModel: UserViewModel= hiltViewModel(),
    //viewModel: DevViewModel= hiltViewModel(),
    navigator: DestinationsNavigator
) {
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
                    text = Constants.TERMS
                )
            },
            text = {
                Column {
                    Button(onClick = {navigator.navigate(WebviewScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("To Doc")}
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
                        me.user.terms_status= Calendar.getInstance().time.toString();
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
