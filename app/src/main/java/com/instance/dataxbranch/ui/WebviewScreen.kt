package com.instance.dataxbranch.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*


@Destination
@Composable
fun WebviewScreen (viewModel: UserViewModel = hiltViewModel(),
               devViewModel: DevViewModel = hiltViewModel(),
               navigator: DestinationsNavigator,
){
    //val db = FirebaseFirestore.getInstance()
    //val me = viewModel.getMeWithAbilities()
    val context = LocalContext.current
    val urls =mapOf( "URLeula" to "https://docs.google.com/document/d/1iaYrjXomvJiIAVhtaOG0dKiv6G9R9Ta4ttfT7tImq04/edit?usp=sharing",
     "URLTermsAndConditions" to "https://docs.google.com/document/d/1ZFoVs44FLIk31WdDUSuyZ-jqCvYDLJZsKtpGKxf5lUI/edit?usp=sharing",
    "URLPrivacyPolicy" to "https://docs.google.com/document/d/1DPjdEkT6Iv0TQ1u5aPtCV7sm8x_Xe2ONWkUgzmLVqoc/edit?usp=sharing")
    val currentDoc= remember {mutableStateOf(urls["URLeula"])}
    Scaffold(

        topBar = { WebToolbar(context,urls,viewModel,navigator) },
        //floatingActionButton = {

            // EditAbilityEntityFloatingActionButton()

    ) {padding ->
        if (viewModel.refreshWebview.value) {
            viewModel.refreshWebview.value=false
            navigator.navigate(WebviewScreenDestination)
        }
        MyContent(viewModel)
}}

@Composable
fun WebToolbar(
    context:Context,
    urls: Map<String, String>,
    viewModel: UserViewModel,
    navigator: DestinationsNavigator

) {

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
                    Button(onClick = {viewModel.currentSite = urls["URLeula"]+""
                        viewModel.refreshWebview.value=true}, modifier= Modifier.padding(2.dp)){ Text("URLeula") }

                    Button(onClick = {viewModel.currentSite = urls["URLTermsAndConditions"]+""
                        viewModel.refreshWebview.value=true}, modifier= Modifier.padding(2.dp)){ Text("URLTermsAndConditions") }

                    Button(onClick = {viewModel.currentSite = urls["URLPrivacyPolicy"]+""
                        viewModel.refreshWebview.value=true}, modifier= Modifier.padding(2.dp)){ Text("URLPrivacyPolicy") }

                   // Button(onClick = {navigator.navigate(DevScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("DevScreen") }
                    Button(onClick = {viewModel.generalRepository.setMe(UserWithAbilities(User(),listOf()))
                                     navigator.navigate(HubScreenDestination)
                                     }, modifier= Modifier.padding(2.dp)){ Text("clear local") }
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
                    Row(modifier= Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { viewModel.getMeWithAbilities().user.terms_status = Calendar.getInstance().time.toString() },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("YES") }

                       /* Button(
                            onClick = { navigator.navigate(MyQuestsScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("to quests") }
                        Button(
                            onClick = { navigator.navigate(HelpScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("help") }*/
                    }
                } else Text("accept")

            }

        })
}

@Composable
fun MyContent(viewModel: UserViewModel) {

    var mUrl: String = "https://docs.google.com/document/d/1ZFoVs44FLIk31WdDUSuyZ-jqCvYDLJZsKtpGKxf5lUI/edit?usp=sharing"
    viewModel.currentSite.let{mUrl=it}
    // Declare a string that contains a url
    //val mUrl = "https://docs.google.com/document/d/1ZFoVs44FLIk31WdDUSuyZ-jqCvYDLJZsKtpGKxf5lUI/edit?usp=sharing"

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(mUrl)
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}
@Composable
fun urlIntent(context:Context, url:String){

    val webIntent: Intent = Uri.parse(url).let { webpage ->
        Intent(Intent.ACTION_VIEW, webpage)
    }
    startActivity(context,webIntent,null)
}