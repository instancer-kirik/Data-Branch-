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
import com.instance.dataxbranch.ui.destinations.AbilitiesScreenDestination

import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.AbilitiesToolbar
import com.instance.dataxbranch.ui.components.AddAbilityEntityAlertDialog

import com.instance.dataxbranch.ui.components.EditAbilityEntityAlertDialog
import com.instance.dataxbranch.ui.components.EditLoadoutAlertDialog

import com.instance.dataxbranch.ui.destinations.LoadoutScreenDestination
import com.instance.dataxbranch.ui.destinations.MyQuestsScreenDestination
import com.instance.dataxbranch.ui.destinations.UserScreenDestination

import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Destination
@Composable
fun AbilitiesScreen(viewModel: UserViewModel = hiltViewModel(),
                     navigator: DestinationsNavigator) {
    val me=viewModel.getMeWithAbilities()
    val context = LocalContext.current
    //var all = remember { mutableStateOf(false) }
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
        Column {
            Text("Attunement: ${viewModel.attunement.value} || Attuned: ${viewModel.attuned.value}")
            Row {
                Button(onClick = {
                    viewModel.allabilities.value = !viewModel.allabilities.value

                }, modifier = Modifier.padding(padding)) { Text("Mode") }
                if(!viewModel.allabilities.value){Text("Selected Character: ${viewModel.getSelectedCharacter().character.name}")}
                else{Text("All Abilities")
                Button(onClick = {viewModel.putAbilityOnCharacter()}){Text("Add to ${viewModel.getSelectedCharacter().character.name}")}}
            }
            AbilitiesLazyColumn(
               // context,
                viewModel,
                abilities = whichAbilitiesToUse(viewModel,viewModel.allabilities),
                modifier = Modifier.padding(2.dp)
            )
            /*me.abilities.forEach {\
            abilityCard(context,it)
        }*/
        }
    }
}

fun whichAbilitiesToUse(viewModel: UserViewModel, all: MutableState<Boolean>): List<AbilityEntity> {
    return if(all.value){
        viewModel.getMeWithAbilities().abilities
    }else viewModel.getSelectedCharacter().abilities
}

@Composable
fun abilityCard( ae: AbilityEntity){
    Row(modifier= Modifier.background(if (ae.inloadout) Color.Blue else Color.Transparent   )/*{
        showToast(context,"casted")*/
    ){
        Column{
        Text("${ae.title} id: ${ae.aid}")
        Row{Text("Cost: "+ae.requiredEnergy)
            Text("   Level: "+ae.getLevel())}
    }

    }
}
@Composable
fun AbilitiesLazyColumn(/*context:Context*/viewModel: UserViewModel, abilities: List<AbilityEntity>, modifier: Modifier){
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
                //context = context,
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
            //context = context,
            viewModel = viewModel,
            ability = rowabilities[0],
            index = idx,
            selected = selectedIndex == idx,
            onClick = onItemClick
        )
            if(rowabilities.size>1){
            idx += 1
            AbilityViewShort(
                //context = context,
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
    //context: Context,
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
    //context: Context,
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

            viewModel.openDialogState.value = true}
        ,
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

fun onSwapLoadoutClick(viewModel: UserViewModel,context: Context){

    if( !viewModel.selectedAE.inloadout){// me.abilities.filter{it.inloadout}.size
        if (viewModel.attunement.value >=viewModel.attuned.value)
        //when adding ability. enough room in attunement
        {
            viewModel.selectedAE.inloadout=!viewModel.selectedAE.inloadout//does this change in character's ability list? SHALLOW COPY?

            viewModel.attuned.value+=1//
            viewModel.syncSelectedAE()
            viewModel.syncAttunement()
           //viewModel.putAbilityOnCharacter(viewModel.selectedAE) not putting on character. but putting in loadout within character
        }else{
            showToast(context, "not enough attunement slots")
        }
    }else{
        //when subtracting ability
        viewModel.selectedAE.inloadout=!viewModel.selectedAE.inloadout
        //should edit within character's ability list maybe not however
        viewModel.syncSelectedAE()
        viewModel.attuned.value-=1
        viewModel.syncAttunement()

        //this removes from character's ability list. not removes from loadout. don't do
        //viewModel.getSelectedCharacter().abilities=viewModel.getSelectedCharacter().abilities.filter{it.aid!=viewModel.selectedAE.aid}

    }






}
