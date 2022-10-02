package com.instance.dataxbranch.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun UserDumpScreen(
    viewModel: UserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    roomQuestViewModel: RoomQuestViewModel = hiltViewModel()


) {
    val context = LocalContext.current
    Scaffold(

        topBar = { UserToolbar(navigator,viewModel,context) },
        floatingActionButton = {
            //AddQuestEntityFloatingActionButton()
        }
    ) { padding ->
        val configuration = LocalConfiguration.current

        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        var expanded by remember { mutableStateOf(true) }
        val backgroundColor = if (isLandscape) {
            MaterialTheme.colors.secondary
        } else {
            MaterialTheme.colors.background
        }
        Spacer(Modifier.requiredHeight(20.dp))
        Row(modifier = Modifier
            .clickable {
                showToast(context, "Clicked c; in userDumpscreen")
            }
            .background(backgroundColor)//if (selected) MaterialTheme.user.colors.secondary else Color.Transparent)
            .fillMaxWidth()
            .padding(12.dp)
        )
        {
            val me = viewModel.getMeWithAbilities()
            Text(me.toString())
            androidx.compose.foundation.lazy.LazyColumn(
                   modifier = Modifier.padding(vertical = 4.dp)
            ){ items(items=viewModel.getAllCharacters()){character->
               Text(character.toString())
            }
            }
    }}}