package com.instance.dataxbranch.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.data.local.CharacterWithStuff
import com.instance.dataxbranch.ui.components.DevToolbar
import com.instance.dataxbranch.ui.components.GOTOButton
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.instance.dataxbranch.utils.navigate
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CharacterSelectScreen (viewModel: UserViewModel = hiltViewModel(),
                navigator: DestinationsNavigator,

) {
    val me = viewModel.getMeWithAbilities()
    val context = LocalContext.current
    val characters : List<CharacterWithStuff> = viewModel.getAllCharacters()
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    Scaffold(

        topBar = {DevToolbar(viewModel,navigator,context) },
        floatingActionButton = {

            CharRefreshFAB(navigator)
        }
    ) { padding->

        if (viewModel.openDialogState3.value) {
            //
        }
        Column{
            Row ( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
            ){
                OutlinedButton(

                    onClick = {
                        expanded = false
                        expanded2 = !expanded2
                    }

                ) {
                    if (expanded2) {
                        Button(onClick = { viewModel.addCharacterEntity("Dummy") }, modifier= Modifier.padding(2.dp)) { Text("Add") }
                        Button(onClick = { navigator.navigate(direction = EditCharacterScreenDestination)/*com.instance.dataxbranch.utils*/}, modifier= Modifier.padding(2.dp)) { Text("Edit") }
                        Button(onClick = { viewModel.delete(viewModel.getSelectedCharacter()) }, modifier= Modifier.padding(2.dp)) { Text("Delete") }
                    } else Text("MOD")
                }


                    OutlinedButton(

                        onClick = {
                            expanded2 = false
                            expanded= !expanded
                        }
                    ) {
                        if (expanded) {
                            Button(onClick = { navigator.navigate(CharacterQuestsScreenDestination)}, modifier= Modifier.padding(2.dp)) { Text("Quest") }
                            Button(onClick = { navigator.navigate(InventoryScreenDestination)}, modifier= Modifier.padding(2.dp)) { Text("Inventory") }
                            Button(onClick = { navigator.navigate(AbilitiesScreenDestination)}, modifier= Modifier.padding(2.dp)) { Text("Abilities") }

                            Button(onClick = { navigator.navigate(CharacterSheetScreenDestination)}, modifier= Modifier.padding(2.dp)) { Text("Sheet") }
                        } else Text("GOTO")
                    }


            }
        ListCharactersLazyColumn(context,viewModel, characters=characters, modifier = Modifier.padding(padding))
    }}
}
@Composable
fun ListCharactersLazyColumn(context:Context,viewModel: UserViewModel, characters: List<CharacterWithStuff>, modifier: Modifier){
    var selectedIndex by remember { mutableStateOf(viewModel.getSelectedCharacterIndex()) }
    val onItemClick = { index: Int ->
        selectedIndex = index
        viewModel.setSelectedCharacter(index)
    }
    val state = rememberLazyListState()
    androidx.compose.foundation.lazy.LazyColumn(state = state,
        modifier = modifier.fillMaxSize(),
    ) {

        itemsIndexed(characters) { index, character ->
            CharacterEntityView(character,
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
fun CharacterEntityView(char: CharacterWithStuff,
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
                //viewModel.setSelectedCharacter(char)

            }
            .background(if (selected) MaterialTheme.colors.secondary else  Color.Transparent )
            .fillMaxWidth()
            .padding(12.dp)) {
            /*Text(
                text = "Index $index",
            )*/
            //if (selected){viewModel.selectedAE = ability}
           // abilityCard(ability)
            CharacterEntityCard( char)

        }
        //Text("DEBUG2")
    }
@Composable
fun CharacterEntityCard( char: CharacterWithStuff){
    Row{
        Column {

            Text(char.character.name)
            Text("Lvl ${char.character.level} ${char.character.race} ${char.character.className}")
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
fun CharRefreshFAB(navigator: DestinationsNavigator,viewModel: UserViewModel = hiltViewModel()
) {
    FloatingActionButton(
        onClick = {
            navigator.navigate(CharacterSelectScreenDestination)
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = Constants.REFRESH
        )
    }
}

