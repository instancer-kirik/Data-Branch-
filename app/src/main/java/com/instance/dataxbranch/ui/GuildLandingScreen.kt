package com.instance.dataxbranch.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun GuildLandingScreen (viewModel: UserViewModel = hiltViewModel(),
               devViewModel: DevViewModel = hiltViewModel(),
               navigator: DestinationsNavigator,
){

    //guild stats: num members, collective xp,
    //has button to go to chat
    //moderator function kick, invite, op,
    //unread messages?
    //inventory?
}