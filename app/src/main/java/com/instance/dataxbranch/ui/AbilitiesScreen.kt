package com.instance.dataxbranch.ui

import android.content.Context

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants

import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.destinations.AbilitiesScreenDestination

import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.AddAbilityEntityAlertDialog

import com.instance.dataxbranch.ui.components.EditAbilityEntityAlertDialog
import com.instance.dataxbranch.ui.components.EditLoadoutAlertDialog

import com.instance.dataxbranch.destinations.LoadoutScreenDestination
import com.instance.dataxbranch.destinations.MyQuestsScreenDestination
import com.instance.dataxbranch.destinations.UserScreenDestination

import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AbilitiesScreen(viewModel: UserViewModel = hiltViewModel(),
                     navigator: DestinationsNavigator) {
    val me=viewModel.getMeWithAbilities()
    val context = LocalContext.current

    Scaffold(

        topBar = { AbilitiesToolbar(context,viewModel,navigator) },
        floatingActionButton = {
            AddAbilityEntityFloatingActionButton()
           // EditAbilityEntityFloatingActionButton()
        }
    ) { padding ->

        if (viewModel.openDialogState.value) {
            AddAbilityEntityAlertDialog()
        }
        if (viewModel.openDialogState2.value) {
            EditAbilityEntityAlertDialog(viewModel,navigator)
        }
        if (viewModel.openDialogState3.value) {
            EditLoadoutAlertDialog()
        }

        Text("Attunement: ${viewModel.attunement.value} || Attuned: ${viewModel.attuned.value}")
        AbilitiesLazyColumn(context,viewModel, abilities = viewModel.getMeWithAbilities().abilities, modifier = Modifier.padding(2.dp))
        /*me.abilities.forEach {\
            abilityCard(context,it)
        }*/

    }
}

@Composable
fun AbilitiesToolbar( context: Context, viewModel:UserViewModel, navigator: DestinationsNavigator) {

        TopAppBar(
            title = { Text(text = "Possible Actions") },
            actions = {

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
                        Button(onClick = { viewModel.openDialogState2.value=true}, modifier=Modifier.padding(2.dp)){Text("edit")}
                        //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("loadout")}
                        Button(onClick = {
                            viewModel.sync()
                           // viewModel.refresh()
                                         navigator.navigate(AbilitiesScreenDestination)
                             }, modifier=Modifier.padding(2.dp)){Text("sync")}
                        Button(onClick = {
                            if( !viewModel.selectedAE.inloadout){// me.abilities.filter{it.inloadout}.size
                            if (viewModel.attunement.value >=viewModel.attuned.value)//when adding ability. enough room in attunement
                            {viewModel.selectedAE.inloadout=!viewModel.selectedAE.inloadout
                                viewModel.attuned.value+=1//
                                viewModel.syncSelectedAE()
                                viewModel.syncAttunement()
                            }else{
                               showToast(context, "not enough attunement slots")
                            }}else{
                                //when subtracting ability
                                viewModel.selectedAE.inloadout=!viewModel.selectedAE.inloadout
                                viewModel.syncSelectedAE()
                                viewModel.attuned.value-=1
                                viewModel.syncAttunement()
                            }





                                         }, modifier=Modifier.padding(2.dp)){Text("swap in Loadout")}
//viewModel.openDialogState3.value=true

                    } else Text("Interact")

                }
                OutlinedButton(
                    onClick = {
                        expanded = !expanded
                        expanded2=false
                    },
                            modifier=Modifier.fillMaxWidth()
                ) {

                    if (expanded) {
                        //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                        //only room for 3 buttons this way
                        Row(modifier=Modifier.fillMaxWidth()) {
                            Button(
                                onClick = { navigator.navigate(UserScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to user") }

                            Button(
                                onClick = { navigator.navigate(MyQuestsScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to quests") }
                            Button(
                                onClick = { navigator.navigate(LoadoutScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to loadout") }

                        }

                    } else Text("navigate")

                }

            })

}

@Composable
fun abilityCard( ae: AbilityEntity){
    Row(modifier= Modifier.background(if (ae.inloadout) Color.Blue else Color.Transparent   )/*{
        showToast(context,"casted")*/
    ){
        Column{
        Text(""+ae.title)
        Row{Text("Cost: "+ae.requiredEnergy)
            Text("   Level: "+ae.getLevel())}
    }

    }
}
@Composable
fun AbilitiesLazyColumn(context:Context,viewModel: UserViewModel, abilities: List<AbilityEntity>, modifier: Modifier){
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int ->
        selectedIndex = index
    }
/*
    Text(
        "placeholder1"//text = "Index $index",
    )*/
    //val me =viewModel.getMeWithAbilities()

    val state = rememberLazyListState()
    androidx.compose.foundation.lazy.LazyColumn(state = state,
        modifier = modifier.fillMaxSize(),
    ) {

        var numLong: Int = 1
        val (longerThan23, rest) = abilities.partition{ it.title!!.length > 23 }
        itemsIndexed(longerThan23) { index, ability ->
            AbilityView(
                context = context,
                viewModel = viewModel,
                ability = ability,
                index = index,
                selected = selectedIndex == index,
                onClick = onItemClick
            )
            numLong = index
        }

    itemsIndexed(rest.chunked(2)) {ix, rowabilities ->
        var idx = 2*ix+numLong+1
        Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){AbilityViewShort(
            context = context,
            viewModel = viewModel,
            ability = rowabilities[0],
            index = idx,
            selected = selectedIndex == idx,
            onClick = onItemClick
        )
            if(rowabilities.size>1){
            idx += 1
            AbilityViewShort(
                context = context,
                viewModel = viewModel,
                ability = rowabilities[1],
                index = idx,
                selected = selectedIndex == idx,
                onClick = onItemClick
            )}
        }

    }/*
            if ((abilities.size - 1) - state.firstVisibleItemIndex == state.layoutInfo.visibleItemsInfo.size - 1) {
                println("Last visible item is actually the last item")
                Log.d(TAG,"Last visible item is actually the last item")
                // do something
            }*/



        //replace count with ability.objectives.size
        //val mObserver = Observer<List<QuestWithObjectives>> { qwe->

    }}
    /*Text(
        "padding"//text = "Index $index",
    )
}*/
@Composable
fun AbilityView(
    context: Context,
    viewModel: UserViewModel,
    ability: AbilityEntity,
    index: Int,
    selected: Boolean,
    onClick: (Int) -> Unit
) {
    //Text("DEBUG")
    Box(modifier = Modifier
        .clickable {
            onClick.invoke(index)
            viewModel.selectedAE=ability

        }
        .background(if (selected) MaterialTheme.colors.secondary else  Color.Transparent )
        .fillMaxWidth()
        .padding(12.dp)) {
        /*Text(
            text = "Index $index",
        )*/
         //if (selected){viewModel.selectedAE = ability}
        abilityCard(ability)

    }
    //Text("DEBUG2")
}
@Composable
fun AbilityViewShort(
    context: Context,
    viewModel: UserViewModel,
    ability: AbilityEntity,
    index: Int,
    selected: Boolean,
    onClick: (Int) -> Unit
) {
    //Text("DEBUG")
    Box(modifier = Modifier
        .clickable {
            onClick.invoke(index)
            viewModel.selectedAE=ability
        }
        .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)

        //.fillMaxWidth()
        .padding(12.dp)) {
        /*Text(
            text = "Index $index",
        )*/
        //if (selected){viewModel.selectedAE = ability}
        abilityCard(ability)

    }
    //Text("DEBUG2")
}
@Composable
fun AddAbilityEntityFloatingActionButton(viewModel: UserViewModel = hiltViewModel()
) {
    FloatingActionButton(
        onClick = {
            viewModel.openDialogState.value = true

        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = Constants.ADD_ABILITY
        )
    }
}


@Composable
fun EditAbilityEntityFloatingActionButton(viewModel: UserViewModel = hiltViewModel()
) {
    FloatingActionButton(
        onClick = {
            viewModel.openDialogState2.value = true
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = Constants.EDIT_ABILITY
        )
    }
}

