package com.instance.dataxbranch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.ui.destinations.WebviewScreenDestination
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*

@Composable
fun  OverwriteLocalDialog(//QviewModel: QuestsViewModel = hiltViewModel(),
    viewModel: UserViewModel = hiltViewModel(),
    //viewModel: DevViewModel= hiltViewModel(),
    navigator: DestinationsNavigator,

) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()
    val me = viewModel.getMeWithAbilities()
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    val currentUser = auth.currentUser
    // if(currentUser != null){ reload(); }
    if (currentUser != null) {
    if (viewModel.termsDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.downloadCloudDialog.value = false
            },
            title = {
                Text(
                    text = Constants.TERMS
                )
            },
            text = {
                Column {
                    Text("CLOUD IS OLDER, OVERWRITE LOCAL; replace local with cloud data?")
                    //Button(onClick = {navigator.navigate(WebviewScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("To Doc") }
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
                        viewModel.overwriteLogIn(context=context, db = FirebaseFirestore.getInstance(),fsid=currentUser.uid)
                        viewModel.downloadCloudDialog.value = false
                        me.user.dateUpdated = me.user.getNow()//Calendar.getInstance().time.toString();
                    }
                ) {
                    Text(
                        text = Constants.OVERWRITE
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.downloadCloudDialog.value = false
                    }
                ) {
                    Text(
                        text = Constants.DISMISS
                    )
                }
            }
        )
    }
}else{AlertDialog(
        onDismissRequest = {
            viewModel.downloadCloudDialog.value = false
        },
        title = {
            Text(
                "YOUR USER AUTH IS NULL"
            )
        },text = {
            Column {
                Text("YOUR USER AUTH IS NULL")}},
        confirmButton = {
            TextButton(
                onClick = {
                    //viewModel.overwriteLogIn(context=context, db = FirebaseFirestore.getInstance(),fsid=currentUser.uid)
                    viewModel.downloadCloudDialog.value = false
                    //me.user.terms_status = me.user.getNow()//Calendar.getInstance().time.toString();
                }
            ) {
                Text(
                    text = Constants.DISMISS
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    viewModel.downloadCloudDialog.value = false
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