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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.showToast
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

    val me = viewModel.getMeWithAbilities()
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
        Column {
            Text("Email")
            TextField(value = email.value, onValueChange = { email.value = it }, maxLines = 1)
            Text("Password")
            TextField(value = password.value,
                onValueChange = { password.value = it }, maxLines = 1)
            val checkedState = remember { mutableStateOf(false) }
            Row{Text("check if creating account")
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    //quest.quest.apply { completed = it }
                    //viewModel.onCheckboxChecked(quest, it)
                })}

            if (!(email.value.isEmpty() || password.value.isEmpty())) {
                if (checkedState.value){
                    TextField(value = matchpassword.value,
                    onValueChange = { matchpassword.value = it }, maxLines = 1)
                    if (password.value == matchpassword.value){
                    Button(onClick = {

                        create(
                            db = db,
                            context,
                            email.value,
                            password.value,
                            viewModel
                        )
                    }) { Text("create") }}
                }else {
                    Button(onClick = {
                        synchronize(
                            db = db,
                            context,
                            email.value,
                            password.value,
                            viewModel
                        )
                    }) { Text("synchronize") }
                }

                    // Button(onClick = { save(db=db,context,email.value,password.value,viewModel)}){Text("save")}
            }
            Button(onClick = {
                navigator.navigate(UserScreenDestination)
            }) { Text("skip")}
        }
    }
}
    fun create(db:FirebaseFirestore,context: Context, email:String, password:String,viewModel: UserViewModel) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
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
