package com.instance.dataxbranch.ui

import android.content.Context
import android.content.res.Configuration

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.AddAbilityEntityAlertDialog
import com.instance.dataxbranch.ui.components.EditAbilityEntityAlertDialog
import com.instance.dataxbranch.destinations.*
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator




    @Destination
    @Composable
    fun LoadoutScreen(viewModel: UserViewModel = hiltViewModel(),
                        navigator: DestinationsNavigator
    ) {
        val me = viewModel.getMeWithAbilities()
        val context = LocalContext.current
        Scaffold(

            topBar = { LoadoutToolbar(navigator) },
            floatingActionButton = {

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
               //
            }
            LoadoutLazyColumn(context,viewModel, abilities = me.abilities.filter{ it.inloadout }, modifier = Modifier.padding(2.dp))
            /*me.abilities.forEach {\
                abilityCard(context,it)
            }*/

        }
    }

    @Composable
    fun LoadoutToolbar( navigator: DestinationsNavigator) {

        TopAppBar(
            title = { Text(text = "Armed Abilities") },
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
                        //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                        //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                        //Button(onClick = {viewModel.openDialogState3.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit loadout") }
                        Button(
                            onClick = { navigator.navigate(DevScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("DevScreen") }

                        Button(onClick = {navigator.navigate(AbilitiesScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("All Abilities")}
                    } else Text("DEBUG")

                }
                OutlinedButton(
                    onClick = {
                        expanded = !expanded
                        expanded2=false
                    }
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
                                onClick = { navigator.navigate(HelpScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("help") }
                        }
                    } else Text("navigate")

                }

            })

    }
@Composable
fun ConfigChangeExample() {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Text("Landscape")
        }
        else -> {
            Text("Portrait")
        }
    }
}
    @Composable
    fun LoadoutabilityCard(context: Context, ae: AbilityEntity,userViewModel: UserViewModel){
        Row(modifier= Modifier.clickable{

            showToast(context,"casted ${ae.OnCasted()} times")
            userViewModel.update(ae)
        }){
            Column{
                Text(""+ae.title)
                Row{
                    Text("Cost: "+ae.requiredEnergy)
                    Text("   Level: "+ae.getLevel())
                }
            }

        }
    }
    @Composable
    fun LoadoutLazyColumn(context: Context, viewModel: UserViewModel, abilities: List<AbilityEntity>, modifier: Modifier){
        //var selectedIndex by remember { mutableStateOf(0) }
        //val onItemClick = { index: Int -> selectedIndex = index}
/*
    Text(
        "placeholder1"//text = "Index $index",
    )*/
        val state = rememberLazyListState()
        androidx.compose.foundation.lazy.LazyColumn(state = state,
            modifier = modifier.fillMaxSize(),
        ) {
            var numLong: Int = 1
            val (longerThan23, rest) = abilities.partition{ it.title!!.length > 23 }
            itemsIndexed(longerThan23) { index, ability ->
                LoadoutAbilityView(
                    context = context,
                    viewModel = viewModel,
                    ability = ability,
                    index = index,
                   // selected = selectedIndex == index,
                    //onClick = onItemClick
                )
                numLong = index
            }

            itemsIndexed(rest.chunked(2)) {ix, rowabilities ->
                var idx = 2*ix+numLong+1
                Row(modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly){LoadoutAbilityViewShort(
                    context = context,
                    viewModel = viewModel,
                    ability = rowabilities[0],
                    index = idx,
                    //selected = selectedIndex == idx,
                    //onClick = onItemClick
                )
                    if(rowabilities.size>1){
                        idx += 1
                        LoadoutAbilityViewShort(
                            context = context,
                            viewModel = viewModel,
                            ability = rowabilities[1],
                            index = idx,
                            //selected = selectedIndex == idx,
                           // onClick = onItemClick
                        )}
                }

            }
            /*if ((abilities.size - 1) - state.firstVisibleItemIndex == state.layoutInfo.visibleItemsInfo.size - 1) {
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
    fun LoadoutAbilityView(
        context: Context,
        viewModel: UserViewModel,
        ability: AbilityEntity,
        index: Int,
       // selected: Boolean,
       // onClick: (Int) -> Unit
    ) {
        //Text("DEBUG")
        Box(modifier = Modifier
            .clickable {
             //   onClick.invoke(index)
            }
            .background(Color.Red)
            .fillMaxWidth()
            .padding(12.dp)) {
            /*Text(
                text = "Index $index",
            )*/
            //if (selected){viewModel.selectedAE = ability}
            LoadoutabilityCard(context,ability, viewModel)

        }
        //Text("DEBUG2")
    }
    @Composable
    fun LoadoutAbilityViewShort(
        context: Context,
        viewModel: UserViewModel,
        ability: AbilityEntity,
        index: Int,
        //selected: Boolean,
        //onClick: (Int) -> Unit
    ) {

        Box(modifier = Modifier
            .clickable {
                //onClick.invoke(index)
            }
            .background (Color.Red)

            //.fillMaxWidth()
            .padding(12.dp)) {Text("                      "+(index-1))
            /*Text(
                text = "Index $index",
            )*/
            //if (selected){viewModel.selectedAE = ability}
            LoadoutabilityCard(context,ability,viewModel)

        }
        //Text("DEBUG2")
    }
    @Composable
    fun bAddAbilityEntityFloatingActionButton(viewModel: UserViewModel = hiltViewModel()
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
    fun bEditAbilityEntityFloatingActionButton(viewModel: UserViewModel = hiltViewModel()
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


