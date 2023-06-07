package com.instance.dataxbranch.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.instance.dataxbranch.NavGraphs
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.*
import com.instance.dataxbranch.destinations.*
import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.instance.dataxbranch.utils.navigate
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun GOTOButton(navigator:DestinationsNavigator, expanded: MutableState<Boolean>, expanded2: MutableState<Boolean>){
    OutlinedButton(

        onClick = {
            expanded2.value = false
            expanded.value = !expanded.value
        }
    ) {
        if (expanded.value) {
            Button(onClick = { navigator.navigate(CharacterQuestsScreenDestination)}) { Text("Quest") }
            Button(onClick = { navigator.navigate(InventoryScreenDestination)}) { Text("Inventory") }
            Button(onClick = { navigator.navigate(AbilitiesScreenDestination)}) { Text("Abilities") }
            Button(onClick = { navigator.navigate(CharacterSheetScreenDestination)}) { Text("Sheet") }
        } else Text("GOTO")
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DayStatusSpinner(
    options: List<String> = listOf("Food", "Bill Payment", "Recharges", "Outing",),
    selectedOption1: String = "DEFAULT",
    onDone: (String) -> Unit = {},
   // onReset: (Boolean) -> Unit = {},
    reset: Boolean =false,
){
    //var resetS by remember { mutableStateOf(reset) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(selectedOption1) }
    var customOptionText by remember { mutableStateOf("") }
    var enterCustomState by remember { mutableStateOf(false) }
    var done by remember { mutableStateOf(false) }
   if (enterCustomState){
    Column {

        TextField(
            value = customOptionText,
            label = { Text("Enter Custom Status") },
            onValueChange = { customOptionText = it },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        Row{Button(onClick = {
            enterCustomState = false
            done = true
            onDone.invoke(customOptionText)
        }) { Text("Done") }
        Text("*Scrollable*")}
    }
}else{
    Column{
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Status") },
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            options.plus(selectedOption1).plus("OTHER").forEach { selectionOption ->
                DropdownMenuItem(onClick = {
                    if (selectionOption == "OTHER") {
                        enterCustomState = true
                    }
                    selectedOptionText = selectionOption
                    expanded = false

                }) {
                    Text(text = selectionOption)
                }}}}
    Button(onClick ={done = true
    onDone.invoke(selectedOptionText)}) { Text("Done") }

    }}



}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EntityTypeSpinner(
    options: List<String> = listOf("A", "B", "C", "D",),
    selectedOption1: String = options.last(),
    onDone: (String) -> Unit = {},
    // onReset: (Boolean) -> Unit = {},
    reset: Boolean = false,
){

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(selectedOption1) }
    //var customOptionText by remember { mutableStateOf("") }
    //var enterCustomState by remember { mutableStateOf(false) }
    var done by remember { mutableStateOf(false) }


    Log.d(TAG, options.toString())

            Column {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }) {
                    TextField(
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = { },
                        label = { Text("Status") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {

                        options/*.plus(selectedOption1).plus("OTHER")*/.forEach { selectionOption ->
                            DropdownMenuItem(onClick = {
                                /*if (selectionOption == "OTHER") {
                                    enterCustomState = true
                                }*/
                                selectedOptionText = selectionOption
                                expanded = false

                            }) {
                                Text(text = selectionOption)
                            }
                        }
                    }
                }
                Button(onClick = {
                    done = true
                    onDone.invoke(selectedOptionText)
                }) { Text("Done") }



        }

}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavSpinner(
    navi: DestinationsNavigator,
    options: List<TypedDestination<*>> = NavGraphs.root.destinations,
    // expanded : MutableState<Boolean>//
){
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0].route) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Categories") },
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(onClick = {
                    selectedOptionText = selectionOption.route
                    expanded = false
                    navi.navigate(rte = selectedOptionText, custom = "true")
                }) {
                    Text(text = selectionOption.route)
                }}}}
}
@Composable
fun DevToolbar(viewModel: UserViewModel, navigator: DestinationsNavigator, context: Context) {

    TopAppBar(
        title = { Text(text = "dev toolbar") },
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
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                    //Button(onClick = {viewModel.openDialogState3.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit loadout") }

                    //Button(onClick = {navigator.navigate(LoginScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("Auth") }
                    NavSpinner(navigator,NavGraphs.root.destinations)





                    Button(onClick = {viewModel.generalRepository.resetAndSet(UserWithAbilities(User(),listOf()))}, modifier= Modifier.padding(2.dp)){ Text("clear local") }
                } else Text("DEBUG ")


   //NavGraphs.root.route

            }
            //startActivity(context, ChannelsActivity.createIntent(context),null)}
            if (expanded||expanded2) {}else{
                ConfigChangeExample()
                Button(onClick = { showToast(context,"CLICK c:") }, modifier= Modifier.padding(2.dp)){ Text("click") }
            }
            OutlinedButton(
                onClick = {
                    expanded = !expanded
                    expanded2=false
                }
            ) {

                if (expanded) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    Row(modifier= Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { navigator.navigate(UserScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("user") }
                        Button(
                            onClick = { navigator.navigate(HubScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("hub") }
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
fun UserToolbar(navigator: DestinationsNavigator,viewModel:UserViewModel,context: Context) {
    TopAppBar(
        title = { Text(text = "MAIN") },
        actions = {

            var expanded by remember { mutableStateOf(false) }
            var expanded2 by remember { mutableStateOf(false) }
            Row{
                OutlinedButton(
                    modifier = Modifier.fillMaxSize(),
                    onClick = {
                        expanded2 = !expanded2
                    }
                ) {

                    if (expanded2) {
                        //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                        //only room for 3 buttons this way
                        Row(modifier = Modifier.fillMaxSize()) {
                            /* val myfsid = viewModel.getMeWithAbilities().user.fsid
                             if( myfsid!="-1"){
                             Button(
                                 onClick = {viewModel.readUserData(context, FirebaseFirestore.getInstance(),myfsid)
                                     navigator.navigate(DevScreenDestination) },
                                 modifier = Modifier.padding(2.dp)
                             ) { Text("sync with valid fsid") }}*/
                            Button(
                                onClick = { navigator.navigate(DevScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("DevScreen") }
                            //Button(onClick = {
                            /*       Button(
                                onClick = { navigator.navigate(MyQuestsScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to quests") }

                            Button(
                                onClick = { navigator.navigate(LoadoutScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to loadout") }
                        }*/
                        }

                    } else Text("DEBUG")
                }
                OutlinedButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {

                    if (expanded) {
                        //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                        //only room for 3 buttons this way
                        Row {
                            Button(
                                onClick = { navigator.navigate(UserScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to user ") }
                            Button(
                                onClick = { navigator.navigate(MyQuestsScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to quests") }

                            Button(
                                onClick = { navigator.navigate(LoadoutScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to loadout") }
                        }
                    } else Text("navigate")}


            }}
    )
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
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    Button(onClick = { viewModel.openDialogState2.value=true}, modifier=Modifier.padding(2.dp)){Text("edit")}
                    Button(onClick = { CoroutineScope(Dispatchers.IO).launch {  viewModel.delete(viewModel.selectedAE) } }) {
                        Text("Delete")
                    }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("loadout")}
                    Button(onClick = {
                        viewModel.sync()
                        // viewModel.refresh()
                        navigator.navigate(AbilitiesScreenDestination)
                    }, modifier=Modifier.padding(2.dp)){Text("sync")}
                    Button(onClick = { onSwapLoadoutClick(viewModel,context) }, modifier=Modifier.padding(2.dp)){Text("swap in Loadout")}
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
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
fun AbilityDetailToolbar(context: Context, viewModel: UserViewModel, navigator: DestinationsNavigator) {
    TopAppBar(
        title = { Text(text = "Possible Actions") },
        actions = {ConfigChangeExample()

            var expanded by remember { mutableStateOf(false) }
            var expanded2 by remember { mutableStateOf(false) }

            OutlinedButton(
                onClick = {
                    expanded=false
                    expanded2 = !expanded2
                }
            ) {

                if (expanded2) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                    //Button(onClick = {viewModel.openDialogState3.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit loadout") }

                    Text("LoadoutScreen")
                    Button(onClick = {navigator.navigate(AbilitiesScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("All Abilities")}
                    Button(onClick = {}, modifier=Modifier.padding(2.dp)){Text("save any changes")}
                } else Text("DEBUG")

            }
            OutlinedButton(
                onClick = {
                    expanded = !expanded
                    expanded2=false
                }
            ) {

                if (expanded) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                            onClick = { navigator.navigate(AbilitiesScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("to abilities") }
                    }
                } else Text("navigate")

            }

        })


}

@Composable
fun CharacterQuestDetailToolbar(context: Context, viewModel: UserViewModel, navigator: DestinationsNavigator,them: List<MutableState<out Any>>) {
    TopAppBar(
        title = { Text(text = "Edit Quest") },
        actions = {
            ConfigChangeExample()

            var expanded by remember { mutableStateOf(false) }
            var expanded2 by remember { mutableStateOf(false) }

            OutlinedButton(
                onClick = {
                    expanded = false
                    expanded2 = !expanded2
                }
            ) {

                if (expanded2) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                    //Button(onClick = {viewModel.openDialogState3.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit loadout") }

                    Button(
                        onClick = { navigator.navigate(DevScreenDestination) },
                        modifier = Modifier.padding(2.dp)
                    ) { Text("DevScreen") }
                    Button(
                        onClick = { navigator.navigate(CharacterQuestsScreenDestination) },
                        modifier = Modifier.padding(2.dp)
                    ) { Text("My Quests") }
                    Button(
                        onClick = { save2(context, navigator, viewModel, them) },
                        modifier = Modifier.padding(2.dp)
                    ) { Text("save any changes") }
                } else Text("DEBUG")

            }
            OutlinedButton(
                onClick = {
                    expanded = !expanded
                    expanded2 = false
                }
            ) {

                if (expanded) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { navigator.navigate(CharacterSelectScreenDestination) },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("to user") }
                        Button(
                            onClick = { navigator.navigate(CharacterQuestsScreenDestination) },
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
fun QuestToolbar(navigator: DestinationsNavigator) {
        TopAppBar(
            title = { Text(text = "MAIN") },
            actions = {

                var expanded by remember { mutableStateOf(false) }
                var expanded2 by remember { mutableStateOf(false) }
                Row{
                    OutlinedButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            expanded2 = !expanded2
                        }
                    ) {

                        if (expanded2) {
                            //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                            //only room for 3 buttons this way
                            Row(modifier = Modifier.fillMaxSize()) {
                                Button(
                                    onClick = { navigator.navigate(CloudQuestsScreenDestination) },
                                    modifier = Modifier.padding(2.dp)
                                ) { Text("to Cloud Quests") }
                                Button(
                                    onClick = { navigator.navigate(MyQuestsScreenDestination) },
                                    modifier = Modifier.padding(2.dp)
                                ) { Text("to My Quests") }
                                Button(
                                    onClick = { navigator.navigate(DevScreenDestination) },
                                    modifier = Modifier.padding(2.dp)
                                ) { Text("DevScreen") }
                                //Button(onClick = {viewModel.read})
                                /*       Button(
                                    onClick = { navigator.navigate(MyQuestsScreenDestination) },
                                    modifier = Modifier.padding(2.dp)
                                ) { Text("to quests") }

                                Button(
                                    onClick = { navigator.navigate(LoadoutScreenDestination) },
                                    modifier = Modifier.padding(2.dp)
                                ) { Text("to loadout") }
                            }*/
                            }

                        } else Text("DEBUG")
                    }
                    OutlinedButton(
                        onClick = {
                            expanded = !expanded
                        }
                    ) {

                        if (expanded) {
                            //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                            //only room for 3 buttons this way
                            Row {
                                Button(
                                    onClick = { navigator.navigate(UserScreenDestination) },
                                    modifier = Modifier.padding(2.dp)
                                ) { Text("to user ") }
                                Button(
                                    onClick = { navigator.navigate(MyQuestsScreenDestination) },
                                    modifier = Modifier.padding(2.dp)
                                ) { Text("to quests") }

                                Button(
                                    onClick = { navigator.navigate(LoadoutScreenDestination) },
                                    modifier = Modifier.padding(2.dp)
                                ) { Text("to loadout") }
                            }
                        } else Text("navigate")}


                }}
        )
    }

@Composable
fun HelpToolbar( navigator: DestinationsNavigator) {

    TopAppBar(
        title = { Text(text = "Help Screen") },
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
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                    //Button(onClick = {viewModel.openDialogState3.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit loadout") }
                    Button(
                        onClick = { navigator.navigate(DevScreenDestination) },
                        modifier = Modifier.padding(2.dp)
                    ) { Text("DevScreen") }

                    Button(onClick = {navigator.navigate(AbilitiesScreenDestination)}, modifier=Modifier.padding(2.dp)){ Text("All Abilities") }
                } else Text("DEBUG")

            }
            OutlinedButton(
                onClick = {
                    expanded = !expanded
                    expanded2=false
                }
            ) {

                if (expanded) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
fun ItemDetailToolbar(context: Context, viewModel: UserViewModel, navigator: DestinationsNavigator,them: List<MutableState<out Any>>) {
    TopAppBar(
        title = { Text(text = "Edit Item") },
        actions = {ConfigChangeExample()

            var expanded by remember { mutableStateOf(false) }
            var expanded2 by remember { mutableStateOf(false) }

            OutlinedButton(
                onClick = {
                    expanded=false
                    expanded2 = !expanded2
                }
            ) {

                if (expanded2) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                    //Button(onClick = {viewModel.openDialogState3.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit loadout") }

                    Button(onClick = {navigator.navigate( DevScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("DevScreen")}
                    Button(onClick = {navigator.navigate(CharacterQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("My Quests")}
                    Button(onClick = { save3(context,navigator,viewModel,them) }, modifier=Modifier.padding(2.dp)){Text("save any changes")}
                } else Text("DEBUG")

            }
            OutlinedButton(
                onClick = {
                    expanded = !expanded

                    expanded2=false

                }
            ) {

                if (expanded) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    Row(modifier=Modifier.fillMaxWidth()){
                        Button(onClick = {navigator.navigate(CharacterSelectScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("to user") }
                        Button(onClick = {navigator.navigate(CharacterQuestsScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("to quests") }
                        Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("to loadout") }}

                } else Text("navigate")

            }

        })


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
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
fun QuestDetailToolbar(context: Context, viewModel: RoomQuestViewModel, navigator: DestinationsNavigator, them: List<MutableState<out Any>>) {
    TopAppBar(
        title = { Text(text = "Edit Quest") },
        actions = {ConfigChangeExample()

            var expanded by remember { mutableStateOf(false) }
            var expanded2 by remember { mutableStateOf(false) }

            OutlinedButton(
                onClick = {
                    expanded=false
                    expanded2 = !expanded2
                }
            ) {

                if (expanded2) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                    //Button(onClick = {viewModel.openDialogState3.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit loadout") }

                    Button(onClick = {navigator.navigate( DevScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("DevScreen")}
                    Button(onClick = {navigator.navigate(MyQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("My Quests")}
                    Button(onClick = { save(context,navigator,viewModel,them) }, modifier=Modifier.padding(2.dp)){Text("save any changes")}
                } else Text("DEBUG")

            }
            OutlinedButton(
                onClick = {
                    expanded = !expanded
                    expanded2=false
                }
            ) {

                if (expanded) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    Row(modifier=Modifier.fillMaxWidth()){
                        Button(onClick = {navigator.navigate(UserScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("to user") }
                        Button(onClick = {navigator.navigate(MyQuestsScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("to quests") }
                        Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("to loadout") }}

                } else Text("navigate")

            }

        })


}

@Composable
fun WebToolbar(
    context:Context,
    urls: Map<String, String>,
    viewModel: UserViewModel,
    navigator: DestinationsNavigator

) {

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
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                    Button(onClick = {viewModel.currentSite = urls["URLeula"]+""
                        viewModel.refreshWebview.value=true}, modifier= Modifier.padding(2.dp)){ Text("URLeula") }

                    Button(onClick = {viewModel.currentSite = urls["URLTermsAndConditions"]+""
                        viewModel.refreshWebview.value=true}, modifier= Modifier.padding(2.dp)){ Text("URLTermsAndConditions") }

                    Button(onClick = {viewModel.currentSite = urls["URLPrivacyPolicy"]+""
                        viewModel.refreshWebview.value=true}, modifier= Modifier.padding(2.dp)){ Text("URLPrivacyPolicy") }

                    // Button(onClick = {navigator.navigate(DevScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("DevScreen") }
//                    Button(onClick = {viewModel.generalRepository.setMe(UserWithAbilities(User(),listOf()))
//                        navigator.navigate(HubScreenDestination)
//                    }, modifier= Modifier.padding(2.dp)){ Text("clear local") }
                } else Text("DOCS")

            }
            OutlinedButton(
                onClick = {
                    expanded = !expanded
                    expanded2=false
                }
            ) {

                if (expanded) {
                    //Button(onClick = {navigator.navigate(CloudQuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    Row(modifier= Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { viewModel.getMeWithAbilities().user.terms_status = Calendar.getInstance().time.toString() },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("YES") }
                        ConfigChangeExample()
                        /* Button(
                             onClick = { navigator.navigate(MyQuestsScreenDestination) },
                             modifier = Modifier.padding(2.dp)
                         ) { Text("to quests") }
                         Button(
                             onClick = { navigator.navigate(HelpScreenDestination) },
                             modifier = Modifier.padding(2.dp)
                         ) { Text("help") }*/
                    }
                } else Text("accept")

            }

        })
}