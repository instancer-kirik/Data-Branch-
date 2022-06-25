package com.instance.dataxbranch.ui



import android.content.Context
import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun QuestDetailScreen (viewModel: RoomQuestViewModel=hiltViewModel(),
                         navigator: DestinationsNavigator,

                         ){
    val context = LocalContext.current
    Scaffold(

        topBar = { QuestDetailToolbar(context,viewModel,navigator) },
        floatingActionButton = {
            //AddQuestEntityFloatingActionButton()
            // EditQuestEntityFloatingActionButton()
        }
    ) { padding ->

        //viewModel.getSelect()
            var desc = remember { mutableStateOf(viewModel.selectedQuest.quest.description + "") }

            var title = remember { mutableStateOf(viewModel.selectedQuest.quest.title + "") }
            var ingredients = remember { mutableStateOf(viewModel.selectedQuest.quest.ingredients) }
            var region = remember { mutableStateOf(viewModel.selectedQuest.quest.region) }
            var sourceUrl = remember { mutableStateOf(viewModel.selectedQuest.quest.sourceUrl) }
            var featuredImage =
                remember { mutableStateOf(viewModel.selectedQuest.quest.featuredImage) }
            var reward = remember { mutableStateOf(viewModel.selectedQuest.quest.reward) }

        //var Mcompleted = remember { mutableStateOf(viewModel.selectedQuest.quest.completed) }

        //INTS
        var rewardxp= remember {mutableStateOf(viewModel.selectedQuest.quest.rewardxp) }
        //var requiredEnergy = remember { mutableStateOf(viewModel.selectedQuest.quest.requiredEnergy ) }
        /*val m = mapOf("desc" to desc,

        "title" to title,
         "damage" to damage,
        "reward" to reward,
        "ingredients" to ingredients,
        "levels"  to levels,
        "levelup"  to levelup,
        "requiredEnergy" to requiredEnergy,)*/
        val checkedState = remember { mutableStateOf(viewModel.selectedQuest.quest.completed) }

        //Questblock("")
        Row{

            Column {
                stringBlock(s = "title", title)
                stringBlock(s = "notes", desc)
                stringBlock(s = "ingredients", ingredients)
                stringBlock(s = "Reward", reward)
                stringBlock(s = "Region", region)
                stringBlock(s = "SourceURL", sourceUrl)

                //bintBlock(s = "damage", damage)
                bintBlock(s = "Reward XP", rewardxp)
            }
            Column {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        viewModel.selectedQuest.quest.apply { completed = it }
                        //viewModel.onCheckboxChecked(it)
                    })

                Button(onClick = {viewModel.selectedQuest.quest.description=desc.value//this isn't changing even though I clearly change it.
                    viewModel.selectedQuest.quest.title =title.value
                    viewModel.selectedQuest.quest.featuredImage=featuredImage.value
                    viewModel.selectedQuest.quest.ingredients = ingredients.value
                    viewModel.selectedQuest.quest.reward = reward.value
                    viewModel.selectedQuest.quest.rewardxp = rewardxp.value
                    viewModel.selectedQuest.quest.sourceUrl = sourceUrl.value
                    /*viewModel.selectedQuest.quest.
                    viewModel.selectedQuest.quest.levels=levels*/
                    viewModel.selectedQuest.quest.let { viewModel.update(it) }

                    viewModel.sync()
                    //viewModel.generalRepository.save(me.user)

                    navigator.navigate(MyQuestsScreenDestination)
                    showToast(context,"Saved " +desc.value)}){
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = Constants.SAVE
                    )
                }
                //blvlupblock(levelups = levelup, levels = levels)
            }}}}

@Composable
fun QuestDetailToolbar(context: Context, viewModel: RoomQuestViewModel, navigator: DestinationsNavigator) {
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
                    Button(onClick = { Log.d(TAG,"Save here")}, modifier=Modifier.padding(2.dp)){Text("save any changes")}
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
fun bintBlock(s: String = "", i: MutableState<Int>){
    var text =remember {
        mutableStateOf(i.value.toString()+"")
    }
    Surface(modifier = Modifier.width(320.dp)) {
        Text(s)
        TextField(

            value = text.value,

            onValueChange = { value ->
                if (value.length >=0) {
                    text.value = value.filter { it.isDigit() }
                }
            }
        )}
}
@Composable
fun blvlupblock(levels:MutableList<String>?, levelups: MutableList<Int>?){


    Surface(modifier = Modifier.width(320.dp)) {
        Column {
            levels?.forEachIndexed { index, s ->
                var text = remember {
                    mutableStateOf(s)
                }
                Column {
                    //Text(levels[index])
                    TextField(
                        value = text.value,
                        maxLines = 1,
                        onValueChange = { value ->
                            text.value= value//.filter { it.isDigit() }
                        }
                    )
                    var text2 = remember {
                        mutableStateOf(levelups!![index])
                    }
                    //text.value
                    Questblock(s = "", i = text2, weight = 1)

                    /*TextField(
                        value = levelups?.get(index).toString(),
                        onValueChange = { value -> levelups?.set(index, value.toInt()) },

                        maxLines = 1,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(8.dp)
                    )*/

                }
            }
        }
    }


}
@Composable
fun bstringBlock(s: String = "", i: MutableState<String>){
    var text = remember {
        mutableStateOf(i.value +"")
    }
    Surface(modifier = Modifier.width(320.dp)) {
        Text(s)
        TextField(

            value = text.value,

            onValueChange = { value ->
                if (value.length >=0) {
                    text.value = value//.filter { it.isDigit() }
                    i.value=value
                }
            }
        )}
}
@Composable
fun Questblock(s:String, i:MutableState<Int>,weight: Int) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SnackbarDefaults.backgroundColor)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {


        Box(modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .background(Color.Yellow)
            .weight(1f)
            .align(
                Alignment.CenterVertically
            )
            .clickable {
                i.value = i.value - 1
                showToast(context = context, msg = "reduced to $i")
            }) {
            Spacer(
                modifier = Modifier
                    .padding(12.dp)
            )
            Text("     _      ", modifier = Modifier.background(Color.Black.copy(alpha = 0.6f)))
        }
        Text("$s Use " + i.value+" times", modifier = Modifier.background(Color.Black.copy(alpha = 0.6f)))

        Box(modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .background(Color.Black)
            .weight(0.5f)
            .align(
                Alignment.CenterVertically
            )
            .clickable {
                i.value = i.value + 1
                showToast(context = context, msg = "increased to $i")
            }) {
            Spacer(
                modifier = Modifier
                    .padding(20.dp)
            )
            Text("+", modifier = Modifier.background(Color.Black.copy(alpha = 0.6f)))
        }

    }
}