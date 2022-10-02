package com.instance.dataxbranch.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import coil.Coil.enqueue
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.core.Constants.TAG
//import com.instance.dataxbranch.data.PredefinedUserCredentials
//import com.instance.dataxbranch.data.UserCredentials
//import com.instance.dataxbranch.data.entities.User
//import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.showToast

import com.instance.dataxbranch.ui.destinations.*
//import com.instance.dataxbranch.social.StreamChat.ChatHelper.connectUser
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
//import io.getstream.chat.android.client.ChatClient

@Destination
@Composable
fun LoginScreen (viewModel: UserViewModel = hiltViewModel(),
               //devViewModel: DevViewModel = hiltViewModel(),
               navigator: DestinationsNavigator,
) {
   // val db = FirebaseFirestore.getInstance()

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
            showToast(context,"true state in login screen")
            viewModel.downloadCloudDialog.value=false
            //OverwriteLocalDialog(viewModel, navigator)
        }
        Column {
            val fsid = viewModel.whoAmI()
            val myfsid = viewModel.getMeWithAbilities().user.fsid
            Text("These should match if logged in: $fsid ...from Auth")
            Text( "$myfsid  ...from local")
            /*Button(
                synchronize())*/
//            ChatClient.instance().getCurrentToken()?.let { Text(it) }

            Text("padding $padding")
            //LoginLogic( context, fsid , myfsid , navigator , viewModel  )


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
            PasswordLogic(
                email,
                password,
                matchpassword,
                checkedState,
                context,
                navigator,
                viewModel
            )

            Button(onClick = {
                navigator.navigate(UserScreenDestination)
            }) { Text("skip") }
        }
    }}
   /* fun streamchatlogin(user:FirebaseUser,forceRefresh:Boolean){
        val user2= io.getstream.chat.android.client.models.User(
                id = "bender",
        extraData = mutableMapOf(
            "name" to "Bender",
            "image" to "https://bit.ly/321RmWb",
        ),
        )

       // connectUser(UserCredentials(
        ChatClient.instance().connectUser(user = user2.apply{
            id = user.uid
            name = user.displayName.toString()
        }, token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZmFuY3ktbW9kZS0wIn0.rSnrWOv8EbsiYzJlvVwqwCgATZ1Magj_fZl-bZyCHKI")
        .enqueue { result ->
            if (result.isSuccess) {
                // Handle success
                Log.d(TAG,"SUCCESS IN LOGINSCREEN")
            } else {
                // Handle error
                Log.d(TAG,"FAILED IN LOGINSCREEN")
            }*//*
            apiKey = PredefinedUserCredentials.API_KEY,
            user = user2.apply {
                id = user.uid
                name = user.displayName.toString()

            },
            token = "user2.getIdToken(forceRefresh).toString()"
        ))
    }}*/
    /*fun createFirebaseUser(db:FirebaseFirestore, context: Context, email:String, password:String, viewModel: UserViewModel): FirebaseUser? {
        var user:FirebaseUser? = null
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful)
                    //else if successful
                    showToast(context, "logged in successfully ${it.result.user?.uid}")
                it.result.user?.uid?.let { it1 -> viewModel.createAndLogMeIn(context,db,fsid= it1) }//pushes any local user or default to cloud
                 user = it.result.user
                user?.let { it1 -> streamchatlogin(it1,true) }
                return@addOnCompleteListener
            }
            .addOnFailureListener{
                showToast(context,"Failed to create user: ${it.message}")
            }

        return user
    }*/

//MY ORIGINAL LOGIN FUNCTIONS, originally dealt with just firestore.
/*fun synchronize(db:FirebaseFirestore,context: Context, email:String, password:String,viewModel: UserViewModel): FirebaseUser? {
    var user:FirebaseUser? = null
    //var user2= io.getstream.chat.android.client.models.User()

    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener
            //else if successful
            showToast(context, "logged in successfully ${it.result.user?.uid}")
            it.result.user?.uid?.let { it1 -> viewModel.logMeIn(context,db,fsid= it1) }//sets user from cloud
            user = it.result.user
            user?.let { it1 -> streamchatlogin(it1,false) }
        }
        .addOnFailureListener{
            showToast(context,"Failed to create user: ${it.message}")
        }


    return user
}*/
/*@Composable
fun LoginLogic(context: Context,fsid: String?,myfsid:String?, navigator: DestinationsNavigator,viewModel: UserViewModel){
    var user: FirebaseUser? =  FirebaseAuth.getInstance().currentUser
    val db:FirebaseFirestore=FirebaseFirestore.getInstance()
    if (fsid == myfsid) {
        if (fsid != "-1") {
            Button(onClick = {
                if (user != null) {
                    streamchatlogin(user,true)
                }
                navigator.navigate(UserScreenDestination)
            }) { Text("Proceed; already logged in") }
        }
    } else if (fsid != null) {//if not equal
        Button(onClick = {
            viewModel.readUserData(context,db, fsid)
            navigator.navigate(UserScreenDestination)
        }) { Text("Pull from Cloud on AuthID") }
    }
}*/
@Composable
fun PasswordLogic(
    email: MutableState<String>,
    password: MutableState<String>,
    matchpassword: MutableState<String>,
    checkedState: MutableState<Boolean>,

    context: Context,
    navigator: DestinationsNavigator,
    viewModel: UserViewModel
) {
    //val db:FirebaseFirestore=FirebaseFirestore.getInstance()

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
                    showToast(context,"Creates user, supposedly")
                    /*createFirebaseUser(
                        db = db,
                        context,
                        email.value,
                        password.value,
                        viewModel
                    )*/

                    navigator.navigate(UserScreenDestination)


                }) { Text("create") }
            }
        } else {
            Button(onClick = {
                showToast(context,"synchronize(db = db, context,email.value,password.value,viewModel)")
                navigator.navigate(UserScreenDestination)
            }) { Text("synchronize to account") }
        }

        // Button(onClick = { save(db=db,context,email.value,password.value,viewModel)}){Text("save")}
    }
}