package com.instance.dataxbranch.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.ui.components.UserToolbar
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FriendsScreen (viewModel: UserViewModel = hiltViewModel(),
                navigator: DestinationsNavigator
) {
    val me = viewModel.getMeWithAbilities()
    val context = LocalContext.current
    Scaffold(

        topBar = { UserToolbar(navigator,viewModel,context) },
        floatingActionButton = {

            // EditAbilityEntityFloatingActionButton()
        }
    ) { _->

        if (viewModel.openDialogState3.value) {
            //
        }
        FriendsLazyColumn(context,viewModel/*, friends=viewModel.getFriends()*/, modifier = Modifier.padding(2.dp))
    }
}
@Composable
fun FriendsLazyColumn(context:Context,viewModel: UserViewModel, /*friends: List<User>*/ modifier: Modifier){
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int ->
        selectedIndex = index
    }
    val state = rememberLazyListState()
    androidx.compose.foundation.lazy.LazyColumn(state = state,
        modifier = modifier.fillMaxSize(),
    ) {
        itemsIndexed(viewModel.getFriends()) { index, friend ->
            FriendView(friend,
                context = context,
                viewModel = viewModel,
                //ability = ability,
                index = index,
                selected = selectedIndex == index,
                onClick = onItemClick)
        }


    }
    }
@Composable
fun FriendView(friend: User,
               context: Context,
               viewModel: UserViewModel,
              //ability: AbilityEntity,
               index: Int,
               selected: Boolean,
               onClick: (Int) -> Unit
    ) {
        //Text("DEBUG")
        Box(modifier = Modifier
            .clickable {
                onClick.invoke(index)
               // viewModel.selectedAE=ability

            }
            .background(if (selected) MaterialTheme.colors.secondary else  Color.Transparent )
            .fillMaxWidth()
            .padding(12.dp)) {
            /*Text(
                text = "Index $index",
            )*/
            //if (selected){viewModel.selectedAE = ability}
           // abilityCard(ability)
            friendCard( friend)

        }
        //Text("DEBUG2")
    }
@Composable
fun friendCard( friend: User){
    Text(friend.uname)
    Text(friend.name)
   /* Row(modifier= Modifier.background(if (ae.inloadout) Color.Blue else Color.Transparent   )*//*{
        showToast(context,"casted")*//*
    ){
        Column{
            Text(""+ae.title)
            Row{
                Text("Cost: "+ae.requiredEnergy)
                Text("   Level: "+ae.getLevel())
            }
        }

    }*/
}