package com.instance.dataxbranch.ui

import android.content.Context

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.firestore.FirestoreResponse
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.AddResponseAlertDialog
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DevScreen (viewModel: UserViewModel =hiltViewModel(),
               devViewModel: DevViewModel =hiltViewModel(),
               navigator: DestinationsNavigator,
              ){
val db = FirebaseFirestore.getInstance()
        val me = viewModel.getMeWithAbilities()
        val context = LocalContext.current

        Scaffold(

            topBar = { DevToolbar(viewModel,navigator) },
            floatingActionButton = {

                // EditAbilityEntityFloatingActionButton()
            }
        ) {padding ->
            if (viewModel.openDialogState3.value) {
                AddResponseAlertDialog()
            }
            Column{
            PayMeBlock()
            ResponseBlock(context=context, me = me, db=db)
                Button(onClick = {navigator.navigate(DefaultScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("Default screen")}
}
}}

@Composable
fun PayMeBlock() {
    Text("Merch and livestreams and such")
    var text = remember {
        mutableStateOf("https://www.__patreon.com/instance_select?fan_landing=true")
    }
    Text("Grant me money. Support the Developer ")
    Text("I'll set up IAP and ads soon. and I'll grant you exclusive goodies.  " )
    Text("Does this count as a game?")
    //Text("cashapp \$Instancer")
    stringBlock(s = "Patreon: ", text)
    /*Column{Text("Future PayToWin features: +ability slots (planned to earn in update)" )
        Text("Wiki tokens")
        Text("special abilites")
        Text("special quests")
        Text("future user qualifier eg Long Star Ranger")///(Rascal, Knight of Guild,)
        Text("So submit bug reports and special features requests or something")
    }*/
}


@Composable
fun ResponseBlock(context: Context, me: UserWithAbilities,db: FirebaseFirestore) {

    var text = remember { mutableStateOf("") }
    var subjecttext = remember { mutableStateOf("") }
    var isVisible = remember { mutableStateOf(true) }
    Column{
    stringBlock(s = "Subject: ", subjecttext)
    stringBlock(s = "Message to dev: ", text)


        if(isVisible.value){
    Button( onClick = {

        db.collection("responses")
            .add(FirestoreResponse(
                subject = subjecttext.value,
                description = text.value,
                author = me.user.uname,
                authorid = me.user.fsid + ""
            ))
            .addOnSuccessListener { showToast(context,"Response submitted! c;") }
            .addOnFailureListener { e -> showToast(context, "Error writing document $e") }
        isVisible.value=false
        //showToast(context,"c;")
    }) {
        Text("Submit Response")
    }}
}}

fun firestorePlayground(context:Context,db: FirebaseFirestore){
    val docData = hashMapOf(
        "stringExample" to "Hello world!",
        "booleanExample" to true,
        "numberExample" to 3.14159265,
        "dateExample" to Timestamp(java.util.Date()),
        "listExample" to arrayListOf(1, 2, 3),
        "nullExample" to null
    )

    val nestedData = hashMapOf(
        "a" to 5,
        "b" to true
    )

    docData["objectExample"] = nestedData

    db.collection("data").document("one")
        .set(docData)
        .addOnSuccessListener { showToast(context,"DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> showToast(context,"Error writing document $e") }


    val city = hashMapOf(
        "name" to "Los Angeles",
        "state" to "CA",
        "country" to "USA"
    )

    db.collection("responses").document("LA")
        .set(city)
        .addOnSuccessListener { showToast(context, "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> showToast(context,"Error writing document $e") }
}

@Composable
fun DevToolbar(viewModel: UserViewModel, navigator: DestinationsNavigator) {

    TopAppBar(
        title = { Text(text = "Armed Abilities") },
        actions = {ConfigChangeExample()

            var expanded by remember { mutableStateOf(false) }
            var expanded2 by remember { mutableStateOf(false) }

            OutlinedButton(
                onClick = {
                    expanded=false
                    expanded2 = !expanded2
                }
            ) {

                if (expanded2) {
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                    //Button(onClick = {viewModel.openDialogState3.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit loadout") }

                    Button(onClick = {navigator.navigate(LoginScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("Auth")}
                    Button(onClick = {navigator.navigate(AbilitiesScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("All Abilities")}
                    Button(onClick = {viewModel.generalRepository.setMe(UserWithAbilities(User(),listOf()))}, modifier=Modifier.padding(2.dp)){Text("clear local")}
                } else Text("DEBUG")

            }
            OutlinedButton(
                onClick = {
                    expanded = !expanded
                    expanded2=false
                }
            ) {

                if (expanded) {
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    Row(modifier=Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { navigator.navigate(UserScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("to user") }
                        Button(
                            onClick = { navigator.navigate(DefaultScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("to hub") }
                        Button(
                            onClick = { navigator.navigate(HelpScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("help") }
                    }
                } else Text("navigate")

            }

        })

}
@Composable//returning context through time?? idk is hard
fun notification(context: Context){

    test(context)
}
fun test(context: Context) : Context {

    showToast(context, "DGFSGSGGFSGJ")
    return context
}