package com.instance.dataxbranch.social.StreamChat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.R
import com.instance.dataxbranch.data.PredefinedUserCredentials
import com.instance.dataxbranch.data.UserCredentials
import com.instance.dataxbranch.destinations.HubScreenDestination
import com.instance.dataxbranch.destinations.UserScreenDestination
import com.instance.dataxbranch.ui.components.OverwriteLocalDialog
import com.instance.dataxbranch.ui.createFirebaseUser
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import io.getstream.chat.android.client.BuildConfig
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun CustomLoginScreen(
    viewModel: UserViewModel = hiltViewModel(),
    devViewModel: DevViewModel = hiltViewModel(),

    //onBackButtonClick: () -> Unit,
   // onLoginButtonClick: (UserCredentials) -> Unit,
) {
    Scaffold(
        topBar = { CustomLoginToolbar(onClick = { devViewModel.navi.navigate(HubScreenDestination) }) },
        content = {padding->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val client = ChatClient.instance()
                val aaa:String = PredefinedUserCredentials.API_KEY
                //val bbb=by remember { mutableStateOf("") }
                //var apiKeyText by remember { mutableStateOf(aaa) }
                var userIdText by remember { mutableStateOf("") }
                var userTokenText by remember { mutableStateOf("") }
                var userNameText by remember { mutableStateOf("") }
                val uid = "some-uid"
                val additionalClaims: MutableMap<String, Any> = HashMap()
                additionalClaims["premiumAccount"] = true
                val user = User(
                    id = "bender",
                    name = "Bender",
                    image = "https://bit.ly/321RmWb",
                )

// Send token back to client
                val isLoginButtonEnabled = aaa.isNotEmpty() &&
                        userIdText.isNotEmpty() &&
                        userTokenText.isNotEmpty()
                val db = FirebaseFirestore.getInstance()

                //val me = viewModel.getMeWithAbilities()
                val context = LocalContext.current
                val email = remember{ mutableStateOf("") }
                val password = remember{ mutableStateOf("") }
                val matchpassword = remember{ mutableStateOf("") }

                Scaffold(

                    //topBar = { DevToolbar(viewModel,navigator) },
                    floatingActionButton = {

                        // EditAbilityEntityFloatingActionButton()
                    }
                ) { padding ->
                    if (viewModel.downloadCloudDialog.value) {
                        OverwriteLocalDialog(viewModel, devViewModel.navi)
                    }
                    Column {
                        val fsid = viewModel.whoAmI()
                        val myfsid = viewModel.getMeWithAbilities().user.fsid
                        Text("These should match if logged in: $fsid ...from Auth")
                        Text(myfsid + "  ...from local")
                        Text("padding $padding")
                        if (fsid == myfsid) {
                            if (fsid != "-1") {
                                Button(onClick = {
                                    devViewModel.navi.navigate(UserScreenDestination)
                                }) { Text("Proceed; already logged in") }
                            }
                        } else if (fsid != null) {//if not equal
                            Button(onClick = {
                                viewModel.readUserData(context, db, fsid)
                                devViewModel.navi.navigate(UserScreenDestination)
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
                    }
                }

                /*CustomLoginInputField(
                    hint = stringResource(id = R.string.custom_login_hint_api_key),
                    value = apiKeyText,
                    onValueChange = { apiKeyText = it },
                )
*/
                CustomLoginInputField(
                    hint = stringResource(id = R.string.custom_login_hint_user_id),
                    value = userIdText,
                    onValueChange = { userIdText = it },
                )

                CustomLoginInputField(
                    hint = stringResource(id = R.string.custom_login_hint_user_token),
                    value =userTokenText,
                    onValueChange = { userTokenText = it },
                )

                CustomLoginInputField(
                    hint = stringResource(id = R.string.custom_login_hint_user_name),
                    value = userNameText,
                    onValueChange = { userNameText = it },
                )

                Spacer(modifier = Modifier.weight(1f))

                CustomLoginButton(
                    enabled = isLoginButtonEnabled,
                    onClick = {
                        val token = createFirebaseUser(
                            db = db,
                            context,
                            email.value,
                            password.value,
                            viewModel
                        )?.getIdToken(true).toString()

/*

                            val tokenProvider = object : TokenProvider {
                                // Make a request to your backend to generate a valid token for the user
                               // override fun loadToken(): String = yourTokenService.getToken(user)
                                override fun loadToken(): String = client.createToken(user)
                            }
*/

                        /* client.connectUser(user, tokenProvider).enqueue { *//* ... *//* }
                            client.connectUser(user, tokenProvider).enqueue(result -> { *//* ... *//* })*/
                        ChatHelper.connectUser(

                                /*FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).then(function(user) {
                                        user.getToken().then(function(token) {
                                            localStorage.setItem("savedToken", token); // store the token
                                        });
    */


                                UserCredentials(
                                    apiKey = aaa,
                                    user = user.apply {
                                        id = userIdText
                                        name = userNameText
                                    },
                                    token = token
                                )
                            )/*.catch(function(error) {
                            // handle error...*/



                    }
                )

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.sdk_version_template, BuildConfig.STREAM_CHAT_VERSION),
                    fontSize = 14.sp,
                    color = ChatTheme.colors.textLowEmphasis
                )
            }
        }
    )
}

@Composable
private fun CustomLoginToolbar(onClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.user_login_advanced_options))
        },
        navigationIcon = {
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.stream_compose_ic_arrow_back),
                    contentDescription = null,
                    tint = Color.Black,
                )
            }
        },
        backgroundColor = Color.White,
        elevation = 0.dp
    )
}

@Composable
private fun CustomLoginInputField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(56.dp),
        value = value,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        label = { Text(hint) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = ChatTheme.colors.textHighEmphasis,
            backgroundColor = ChatTheme.colors.inputBackground,
            cursorColor = ChatTheme.colors.primaryAccent,
            focusedIndicatorColor = ChatTheme.colors.primaryAccent,
            focusedLabelColor = ChatTheme.colors.primaryAccent,
            unfocusedLabelColor = ChatTheme.colors.textLowEmphasis,
        )
    )
}

@Composable
private fun CustomLoginButton(
    enabled: Boolean,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ChatTheme.colors.primaryAccent,
            disabledBackgroundColor = ChatTheme.colors.disabled,
        ),
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = R.string.custom_login_button_text),
            fontSize = 16.sp,
            color = Color.White
        )
    }
}