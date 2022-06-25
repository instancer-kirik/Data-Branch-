package com.instance.dataxbranch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.ui.components.AddQuestAlertDialog
import com.instance.dataxbranch.ui.components.AddResponseAlertDialog
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DevScreen (viewModel: UserViewModel =hiltViewModel(),
               devViewModel: DevViewModel =hiltViewModel(),
               navigator: DestinationsNavigator,){

        val me = viewModel.getMeWithAbilities()
        val context = LocalContext.current
        Scaffold(

            topBar = { LoadoutToolbar(viewModel,navigator) },
            floatingActionButton = {

                // EditAbilityEntityFloatingActionButton()
            }
        ) {padding ->
            if (viewModel.openDialogState3.value) {
                AddResponseAlertDialog()
            }
            Column{
            PayMeBlock()
            ResponseBlock(devViewModel = devViewModel, me = me)
}
}}

@Composable
fun PayMeBlock() {
    Column{Text("Future PayToWin features: More ability slots (planned earn with level in update)" )
        Text("Wiki tokens")
        Text("special abilites")
        Text("special quests")
        Text("future user qualifier eg Black Star Ranger")///(Rascal, Knight of Guild,)
    }
}


@Composable
fun ResponseBlock(devViewModel:DevViewModel,me: UserWithAbilities) {
    var text = remember { mutableStateOf("") }
    var subjecttext = remember { mutableStateOf("") }
    Column{
    stringBlock(s = "Subject: ", subjecttext)
    stringBlock(s = "Message to dev: ", text)


    Button(onClick = {
        devViewModel.addResponse(
            subject = subjecttext.value,
            description = text.value,
            author = me.user.uname,
            authorid = me.user.fsid + ""
        )
    }) {
        Text("Submit Response")
    }}
}