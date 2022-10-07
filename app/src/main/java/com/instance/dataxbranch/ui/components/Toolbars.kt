package com.instance.dataxbranch.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.*
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                    //only room for 3 buttons this way
                    //Button(onClick = { viewModel.openDialogState2.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit") }
                    //Button(onClick = {navigator.navigate(LoadoutScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("loadout") }
                    //Button(onClick = {viewModel.openDialogState3.value=true}, modifier= Modifier.padding(2.dp)){ Text("edit loadout") }

                    Button(onClick = {navigator.navigate(LoginScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("Auth") }
                    Button(onClick = {navigator.navigate(AbilitiesScreenDestination)}, modifier= Modifier.padding(2.dp)){ Text("All Abilities") }





                    Button(onClick = {viewModel.generalRepository.resetAndSet(UserWithAbilities(User(),listOf()))}, modifier= Modifier.padding(2.dp)){ Text("clear local") }
                } else Text("DEBUG")

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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                        //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                        //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                        //only room for 3 buttons this way
                        Row() {
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                            //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                            //only room for 3 buttons this way
                            Row(modifier = Modifier.fillMaxSize()) {
                                Button(
                                    onClick = { navigator.navigate(QuestsScreenDestination) },
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
                            //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
                            //only room for 3 buttons this way
                            Row() {
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    //Button(onClick = {navigator.navigate(QuestsScreenDestination)}, modifier=Modifier.padding(2.dp)){Text("to cloud quests")}
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
                    Button(onClick = {viewModel.generalRepository.setMe(UserWithAbilities(User(),listOf()))
                        navigator.navigate(HubScreenDestination)
                    }, modifier= Modifier.padding(2.dp)){ Text("clear local") }
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
                    Row(modifier= Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { viewModel.getMeWithAbilities().user.terms_status = Calendar.getInstance().time.toString() },
                            modifier = Modifier.padding(2.dp)
                        ) { Text("YES") }

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