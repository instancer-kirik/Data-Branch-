package com.instance.dataxbranch.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.AddResponseAlertDialog
import com.instance.dataxbranch.ui.components.OverwriteLocalDialog
import com.instance.dataxbranch.ui.destinations.DefaultScreenDestination
import com.instance.dataxbranch.ui.destinations.UserScreenDestination
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LoginScreen (viewModel: UserViewModel = hiltViewModel(),
               devViewModel: DevViewModel = hiltViewModel(),
               navigator: DestinationsNavigator,
) {
    val db = FirebaseFirestore.getInstance()

    //val me = viewModel.getMeWithAbilities()
    val context = LocalContext.current
    val email = remember{ mutableStateOf("")}
    val password = remember{ mutableStateOf("")}
    val matchpassword = remember{ mutableStateOf("")}

    Scaffold(

        //topBar = { DevToolbar(viewModel,navigator) },
        floatingActionButton = {

            // EditAbilityEntityFloatingActionButton()
        }
    ) { padding ->
        if (viewModel.downloadCloudDialog.value) {
            OverwriteLocalDialog(viewModel, navigator)
        }
        Column {
            val fsid = viewModel.whoAmI()
            val myfsid = viewModel.getMeWithAbilities().user.fsid
            Text("These should match if logged in: $fsid ...from Auth")
            Text(myfsid + "  ...from local")
            if (fsid == myfsid) {
                if (fsid != "-1") {
                    Button(onClick = {
                        navigator.navigate(UserScreenDestination)
                    }) { Text("Proceed; already logged in") }
                }
            } else if (fsid != null) {//if not equal
                Button(onClick = {
                    viewModel.readUserData(context, db, fsid)
                    navigator.navigate(UserScreenDestination)
                }) { Text("Pull from Cloud on AuthID") }
            }


            //Text("Above line pulls from FirestoreAuth, if valid, you are already logged in, may skip")
            Text("Email")
            TextField(value = email.value, onValueChange = { email.value = it }, singleLine = true)
            Text("Password")
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            val checkedState = remember { mutableStateOf(false) }
            Row {
                Text("check if creating account")
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        //quest.quest.apply { completed = it }
                        //viewModel.onCheckboxChecked(quest, it)
                    })
            }

            if (!(email.value.isEmpty() || password.value.isEmpty())) {
                if (checkedState.value) {
                    Text("Match Password")
                    TextField(value = matchpassword.value,
                        onValueChange = { matchpassword.value = it },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation()
                    )
                    if (password.value == matchpassword.value) {
                        Button(onClick = {

                            create(
                                db = db,
                                context,
                                email.value,
                                password.value,
                                viewModel
                            )

                            navigator.navigate(UserScreenDestination)


                        }) { Text("create") }
                    }
                } else {
                    Button(onClick = {
                        synchronize(
                            db = db,
                            context,
                            email.value,
                            password.value,
                            viewModel
                        )
                        navigator.navigate(UserScreenDestination)
                    }) { Text("synchronize to account") }
                }

                // Button(onClick = { save(db=db,context,email.value,password.value,viewModel)}){Text("save")}
            }
            Button(onClick = {
                navigator.navigate(UserScreenDestination)
            }) { Text("skip") }
        }
    }}

    fun create(db:FirebaseFirestore,context: Context, email:String, password:String,viewModel: UserViewModel) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                    //else if successful
                    showToast(context, "logged in successfully ${it.result.user?.uid}")
                it.result.user?.uid?.let { it1 -> viewModel.createAndLogMeIn(context,db,fsid= it1) }//pushes any local user or default to cloud

            }
            .addOnFailureListener{
                showToast(context,"Failed to create user: ${it.message}")
            }

    }
fun synchronize(db:FirebaseFirestore,context: Context, email:String, password:String,viewModel: UserViewModel) {

    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener
            //else if successful
            showToast(context, "logged in successfully ${it.result.user?.uid}")
            it.result.user?.uid?.let { it1 -> viewModel.logMeIn(context,db,fsid= it1) }//sets user from cloud
        }
        .addOnFailureListener{
            showToast(context,"Failed to create user: ${it.message}")
        }

}
