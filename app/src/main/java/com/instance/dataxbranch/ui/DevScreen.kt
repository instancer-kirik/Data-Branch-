package com.instance.dataxbranch.ui

import android.content.Context
import android.util.Log

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
import com.instance.dataxbranch.core.Constants.TAG

import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.cloud.CloudResponse
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.*
import com.instance.dataxbranch.destinations.*
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException


@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun DevScreen (viewModel: UserViewModel =hiltViewModel(),
               devViewModel: DevViewModel =hiltViewModel(),
               navigator: DestinationsNavigator,
              ){

        val me = viewModel.getMeWithAbilities()
        val context = LocalContext.current

        Scaffold(

            topBar = { DevToolbar(viewModel,navigator,context) },
            floatingActionButton = {

                // EditAbilityEntityFloatingActionButton()
            }
        ) {padding ->

            if (devViewModel.openDialogState.value) {
                AddResponseAlertDialog(viewModel=devViewModel)
            }
            Column{
                Row {
                    Column{
                    /*val crashButton=*/Button(onClick = {
                    throw RuntimeException("Test Crash") // Force a crash
                }
                ) { Text("Crash Button") }


                    Text("HTTP TEST: ${devViewModel.HTTPResponse}\n")
                    httpButton1(viewModel = devViewModel)
                }
                    //PayMeBlock()
                }
                //dragBox()//BottomSheet()
                BottomSheetContainer()

                ResponseBlock(context=context, me = me, )
                Button(onClick = {navigator.navigate(HubScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("Default screen")}
}
}}

@Composable
fun PayMeBlock() {
    Column{
    Text("Merch and livestreams and such. TO DO")
    val text = remember {
        mutableStateOf("https://www.__patreon.com/instance_select?fan_landing=true")
    }
    Text("Support the Developer ")
   // Text("I'll set up IAP and ads soon. and I'll grant you exclusive goodies.  " )

    //Does this count as a game?
    Text("cashapp \$Instancer")
    stringBlock(s = "Patreon: ", text)
    /*Column{Text("Future PayToWin features: +ability slots (planned to earn in update)" )
        Text("Wiki tokens")
        Text("special abilites")
        Text("special quests")
        Text("future user qualifier eg Long Star Ranger")///(Rascal, Knight of Guild,)
        Text("So submit bug reports and special features requests or something")
    }*/
}}


@Composable
fun ResponseBlock(context: Context, me: UserWithAbilities,db: String="database") {

    var text = remember { mutableStateOf("") }
    var subjecttext = remember { mutableStateOf("") }
    var isVisible = remember { mutableStateOf(true) }
    Column{
        Text("Here you can submit responses, bug reports, and requests")
        Text("not currently working; no cloud database currently. removed firestore")
    stringBlock(s = "Subject: ", subjecttext)
    stringBlock(s = "Message to dev: ", text)


        if(isVisible.value){
    Button( onClick = {
        showToast(context,"NOT YET IMPLEMENTED. TBD adds to $db $text")
        /*db.collection("responses")
            .add(CloudResponse(
                subject = subjecttext.value,
                description = text.value,
                author = me.user.uname,
                authorid = me.user.fsid + ""
            ))
            .addOnSuccessListener { showToast(context,"Response submitted! c;") }
            .addOnFailureListener { e -> showToast(context, "Error writing document $e") }*/
        isVisible.value=false
        //showToast(context,"c;")
    }) {
        Text("Submit Response")
        Text("does not work, just ping me on discord: instancer_kirik#3040 ")
    }}
}}
//
//fun firestorePlayground(context:Context,db: FirebaseFirestore){
//    val docData = hashMapOf(
//        "stringExample" to "Hello world!",
//        "booleanExample" to true,
//        "numberExample" to 3.14159265,
//        "dateExample" to Timestamp(java.util.Date()),
//        "listExample" to arrayListOf(1, 2, 3),
//        "nullExample" to null
//    )
//
//    val nestedData = hashMapOf(
//        "a" to 5,
//        "b" to true
//    )
//
//    docData["objectExample"] = nestedData
//
//    db.collection("data").document("one")
//        .set(docData)
//        .addOnSuccessListener { showToast(context,"DocumentSnapshot successfully written!") }
//        .addOnFailureListener { e -> showToast(context,"Error writing document $e") }
//
//
//    val city = hashMapOf(
//        "name" to "Los Angeles",
//        "state" to "CA",
//        "country" to "USA"
//    )
//
//    db.collection("responses").document("LA")
//        .set(city)
//        .addOnSuccessListener { showToast(context, "DocumentSnapshot successfully written!") }
//        .addOnFailureListener { e -> showToast(context,"Error writing document $e") }
//}

@Composable//returning context through time?? idk is hard
fun notification(context: Context){

    test(context)
}
fun test(context: Context) : Context {

    showToast(context, "DGFSGSGGFSGJ")
    return context
}
@Composable
fun notePadSlide(viewModel: DevViewModel,context:Context){


}
@Composable
fun notePadButton(viewModel: DevViewModel){
    Button(onClick = { viewModel.openNoteDialogState.value=true},
        modifier = Modifier.padding(2.dp)
    ) { Text("^NotePad^") }


}

@Composable
fun httpButton1(URL: String = /*"https://publicobject.com/helloworld.txt"*/ "https://0.0.0.0:8000/sql",viewModel: DevViewModel){
    Button(onClick={
        if (URL.isNotEmpty()){
            //create http client
            val DATA = "INFO FOR DB;"
            val okhttpClient = OkHttpClient()
            //build request
            val request = Request.Builder()
                .url(URL)
                .header("Accept", "application/json")
                .addHeader("DB","my_db")
                .addHeader("NS","my_ns")
                .addHeader("user", "root:root")
                .addHeader("data","${DATA}")//post(string.toRequestBody())
                //.addHeader("data", "${DATA}")
                .build()
            //enqueue the request and handle callbacks/??
            Log.i("Request",request.toString())
            okhttpClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e:IOException){
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.i("Response","recieved Response from server")
                    response.use{
                        if(!response.isSuccessful)
                        {
                            Log.e("HTTP ERROR","Unsuccessful loading")
                        }
                        else{
                            //fetch the body of the response
                            val body = response.body?.string()
                            Log.i("Response","$body")
                            viewModel.HTTPResponse = body+""
                        }
                    }}
            })
        }else{
            Log.d(TAG,"URL IS EMPTY @DEVSCREEN HTTPBUTTON1"
            )
        }
                   },modifier=Modifier.padding(2.dp)){Text("HTTPButton")}


}