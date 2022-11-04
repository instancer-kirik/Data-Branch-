package com.instance.dataxbranch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.hilt.navigation.compose.hiltViewModel
//import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.instance.dataxbranch.core.Constants

import com.instance.dataxbranch.ui.calendar.StaticCalendar
import com.instance.dataxbranch.ui.components.QuestToolbar
import com.instance.dataxbranch.ui.viewModels.UserViewModel

import kotlinx.coroutines.*


//viewModel: RoomQuestViewModel = hiltViewModel(),
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalStdlibApi::class)

@Destination
@Composable
fun CalendarScreen(

    uViewModel: UserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,


    ) {
    Scaffold(modifier = Modifier.drawBehind { LinearGradient(listOf(Color.Blue,Color.LightGray)) },

        topBar = { QuestToolbar(navigator) },
        floatingActionButton = {
            AddFloatingActionButtonCalendar()
        }
    ) { padding ->

        if (recomposeState.value){
            recomposeState.value =false
            }
      /*  if (uViewModel.characterDialogState.value) {
            AddQuestEntityOnCharacterAlertDialog()
        }*/
        Column {
            val stuff = uViewModel.getCalendarStuff()
            StaticCalendar(modifier= Modifier,stuff)
            Text("$padding\n$stuff")


        }
    }
    }



@Composable
fun AddFloatingActionButtonCalendar(uViewModel: UserViewModel = hiltViewModel()
) {
    FloatingActionButton(
        onClick = {

            //uViewModel.characterDialogState.value = true
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = Constants.ADD_QUEST
        )
    }
}