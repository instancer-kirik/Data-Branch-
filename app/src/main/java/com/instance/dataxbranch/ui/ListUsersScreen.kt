package com.instance.dataxbranch.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.firestore.FirestoreUser
import com.instance.dataxbranch.destinations.ListUsersScreenDestination
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ListUsersScreen (viewModel: UserViewModel = hiltViewModel(),
                navigator: DestinationsNavigator,

) {
    val me = viewModel.getMeWithAbilities()
    val context = LocalContext.current
    val listUsers : List<FirestoreUser> = viewModel.getAllFirestoreUsers(context)
    Scaffold(

        topBar = {DevToolbar(viewModel,navigator,context) },
        floatingActionButton = {

            RefreshFAB(navigator)
        }
    ) { p->

        if (viewModel.openDialogState3.value) {
            //
        }
        ListUsersLazyColumn(context,viewModel, listUsers=listUsers, modifier = Modifier.padding(p))
    }
}
@Composable
fun ListUsersLazyColumn(context:Context,viewModel: UserViewModel, listUsers: List<FirestoreUser>, modifier: Modifier){
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int ->
        selectedIndex = index
    }
    val state = rememberLazyListState()
    androidx.compose.foundation.lazy.LazyColumn(state = state,
        modifier = modifier.fillMaxSize(),
    ) {

        itemsIndexed(listUsers) { index, user ->
            FSUserView(user,
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
fun FSUserView(user: FirestoreUser,
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
            FSuserCard( user)

        }
        //Text("DEBUG2")
    }
@Composable
fun FSuserCard( user: FirestoreUser){
    Row{
        Column {
            Text(user.uname)
            Text(user.name)
        }
    }
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
@Composable
fun RefreshFAB(navigator: DestinationsNavigator,viewModel: UserViewModel = hiltViewModel()
) {
    FloatingActionButton(
        onClick = {
            navigator.navigate(ListUsersScreenDestination)
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = Constants.REFRESH
        )
    }
}
