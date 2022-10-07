package com.instance.dataxbranch.ui

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
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.ui.components.AddAbilityEntityAlertDialog
import com.instance.dataxbranch.ui.components.AddQuestAlertDialog
import com.instance.dataxbranch.ui.components.EditAbilityEntityAlertDialog
import com.instance.dataxbranch.ui.components.HelpToolbar
import com.instance.dataxbranch.ui.viewModels.QuestsViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HelpScreen (viewModel: UserViewModel = hiltViewModel(),
                qViewModel: QuestsViewModel = hiltViewModel(),
                navi: DestinationsNavigator
    ) {
        val me = viewModel.getMeWithAbilities()
        val context = LocalContext.current
        Scaffold(

            topBar = { HelpToolbar(navi) },
            floatingActionButton = {

                // EditAbilityEntityFloatingActionButton()
            }
        ) { padding ->

            if (viewModel.openDialogState3.value) {
                AddQuestAlertDialog(qViewModel,"Help Quest ... briefly describe your issue to others",
                    "Contact ${me.user.uname} by: --edit or be contacted--",
                viewModel.openDialogState3)
            }
            Column {
                Button(
                    onClick = {
                        navi.navigate(QuietScreenDestination)
                    },
                    modifier = Modifier.padding(2.dp)
                ) { Text("GROUND AND CENTER") }
                Button(
                    onClick = {
                        viewModel.openDialogState3.value=true
                        //qViewModel.useCases.addQuest("Help ${me.user.uname}","")
                       // navi.navigate(QuestDetailScreenDestination)
                        //
                    },
                    modifier = Modifier.padding(2.dp)
                ) { Text("Post Help Quest") }
                Button(
                    onClick = {
                        navi.navigate(FriendsScreenDestination)
                        TODO("this isn't done. make so not dependent on friends.")
                    },
                    modifier = Modifier.padding(2.dp)
                ) { Text("Contact confidant") }
                Button(
                    onClick = {
                        navi.navigate(DevScreenDestination)
                    },
                    modifier = Modifier.padding(2.dp)
                ) { Text("Contact Admin") }

                helpMe(viewModel, navi)
                helpSomeone()
                //LoadoutLazyColumn(context,viewModel, abilities = me.abilities.filter{ it.inloadout }, modifier = Modifier.padding(2.dp))
                /*me.abilities.forEach {\
                abilityCard(context,it)
            }*/

            }
    }
}

@Composable fun helpMe(viewModel: UserViewModel,navi: DestinationsNavigator){



   /* post quest,
    contact confidant,
    contact admin
    ?translate,
    grounding exercises,
    
*/
}
/*Planner:
identify{ goals, actors, obstacles, requirements, resources, outcomes, etc}
Decide Strategy
Step 3: Get required items
Create Quest?*/
@Composable fun helpSomeone(){
   /*

   post quest,
    contact authorities,
    consoling tips*/
   /* Consoling tips:
    Just listen
            Don't downplay their feelings.
    Distract.
    music.
    incense.
    etc
    Control what you can so you're better suited tomorrow*/
}

