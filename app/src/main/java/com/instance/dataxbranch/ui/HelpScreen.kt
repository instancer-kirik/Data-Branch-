package com.instance.dataxbranch.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.ui.components.AddAbilityEntityAlertDialog
import com.instance.dataxbranch.ui.components.EditAbilityEntityAlertDialog
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HelpScreen (viewModel: UserViewModel = hiltViewModel(),
                navigator: DestinationsNavigator
    ) {
        val me = viewModel.getMeWithAbilities()
        val context = LocalContext.current
        Scaffold(

            topBar = { LoadoutToolbar(viewModel,navigator) },
            floatingActionButton = {

                // EditAbilityEntityFloatingActionButton()
            }
        ) { padding ->

            if (viewModel.openDialogState3.value) {
                //
            }
            helpMe()
            helpSomeone()
            LoadoutLazyColumn(context,viewModel, abilities = me.abilities.filter{ it.inloadout }, modifier = Modifier.padding(2.dp))
            /*me.abilities.forEach {\
                abilityCard(context,it)
            }*/


    }
}

@Composable fun helpMe(){

   /* post quest,
    contact confidiant,
    contact admin
    ?translate,
    grounding exercises,
    
*/
}
@Composable fun helpSomeone(){
   /*
   link app
   post quest,
    contact authorities,
    consoling tips*/

}
@Composable fun settingsAlertEvent(){}