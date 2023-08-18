package com.instance.dataxbranch.ui



import android.content.Context


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

import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.QuestDetailToolbar
import com.instance.dataxbranch.destinations.*
import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun QuestDetailScreen (viewModel: RoomQuestViewModel=hiltViewModel(),
                         navigator: DestinationsNavigator,

                         ){
    var desc = remember { mutableStateOf(viewModel.selectedQuest.quest.description + "") }

    var title = remember { mutableStateOf(viewModel.selectedQuest.quest.title + "") }
    var ingredients = remember { mutableStateOf(viewModel.selectedQuest.quest.ingredients) }
    var region = remember { mutableStateOf(viewModel.selectedQuest.quest.region) }
    //var sourceUrl = remember { mutableStateOf(viewModel.selectedQuest.quest.sourceUrl) }
    var featuredImage = remember { mutableStateOf(viewModel.selectedQuest.quest.featuredImage) }
    var reward = remember { mutableStateOf(viewModel.selectedQuest.quest.reward) }
    var rewardxp= remember {mutableStateOf(viewModel.selectedQuest.quest.rewardxp) }
    val checkedState = remember { mutableStateOf(viewModel.selectedQuest.quest.completed) }
    val questFields = remember {
        QuestFields(
            title = title,
            desc = desc,
            checkedState = checkedState,
            ingredients = ingredients,
            reward = reward,
            rewardxp = rewardxp,
            region = region,
            featuredImage = featuredImage,
            isHabit = mutableStateOf(false),
        )
    }
    val context = LocalContext.current
    Scaffold(

        topBar = { QuestDetailToolbar(context,viewModel,navigator,questFields) },
        floatingActionButton = {
            //AddQuestEntityFloatingActionButton()
            // EditQuestEntityFloatingActionButton()
        }
    ) { padding ->

       //viewModel.getSelect()

        //var Mcompleted = remember { mutableStateOf(viewModel.selectedQuest.quest.completed) }

        //INTS

        //var requiredEnergy = remember { mutableStateOf(viewModel.selectedQuest.quest.requiredEnergy ) }
        /*val m = mapOf("desc" to desc,

        "title" to title,
         "damage" to damage,
        "reward" to reward,
        "ingredients" to ingredients,
        "levels"  to levels,
        "levelup"  to levelup,
        "requiredEnergy" to requiredEnergy,)*/


        //Questblock("")
        Row(modifier = Modifier.padding(padding)){

            Column {
                stringBlock(s = "title", questFields.title)
                stringBlock(s = "notes", questFields.desc)
                stringBlock(s = "ingredients", questFields.ingredients)
                stringBlock(s = "Reward", questFields.reward)
                stringBlock(s = "Region", questFields.region)
                // stringBlock(s = "SourceURL", questFields.sourceUrl)

                // bintBlock(s = "damage", questFields.damage)
                bintBlock(s = "Reward XP", questFields.rewardxp)
            }
            Column {
                questFields.checkedState.value?.let {
                    Checkbox(
                        checked = it,
                        onCheckedChange = {
                            questFields.checkedState.value = it
                            viewModel.selectedQuest.quest.apply { completed = it }
                            //viewModel.onCheckboxChecked(it)
                        })
                }

                Button(onClick = {save(context,navigator,viewModel,questFields)}){
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = Constants.SAVE
                    )
                }
                //blvlupblock(levelups = levelup, levels = levels)
            }}}}

/*fun save(
    context: Context,
    navigator: DestinationsNavigator,
    viewModel: RoomQuestViewModel,
    them: List<MutableState<out Any>>
) {
    viewModel.selectedQuest.quest.title = them[0].value.toString()
    viewModel.selectedQuest.quest.description =
        them[1].value.toString()//this isn't changing even though I clearly change it.
    viewModel.selectedQuest.quest.completed = them[2].value as Boolean

    viewModel.selectedQuest.quest.ingredients = them[3].value as String
    viewModel.selectedQuest.quest.reward =them[4].value as String
    viewModel.selectedQuest.quest.rewardxp = them[5].value as Int
    //viewModel.selectedQuest.quest.sourceUrl =  them[6].value as String
    viewModel.selectedQuest.quest.featuredImage = them[6].value as String
    *//*viewModel.selectedQuest.quest.
    viewModel.selectedQuest.quest.levels=levels*//*
    viewModel.selectedQuest.quest.let { viewModel.update(it) }

    viewModel.sync()
    //viewModel.generalRepository.save(me.user)

    navigator.navigate(MyQuestsScreenDestination)
    showToast(context, "Saved " + them[1].value.toString())
}*/

@Composable
fun stringBlock(s: String = "", i: MutableState<String?>){
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

data class QuestFields(
    val title: MutableState<String>,
    val desc: MutableState<String>,
    val checkedState: MutableState<Boolean?>,
    val ingredients: MutableState<String?>,
    val reward: MutableState<String?>,
    val rewardxp: MutableState<Int?>,
    val region: MutableState<String?>,
    val featuredImage: MutableState<String?>,
    val isHabit: MutableState<Boolean?>,
)
@Composable
fun bintBlock(s: String = "", i: MutableState<Int?>){
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
//val them =listOf(title,desc,checkedState,ingredients,reward,rewardxp,region,sourceUrl, featuredImage)
fun save(
    context: Context,
    navigator: DestinationsNavigator,
    viewModel: RoomQuestViewModel,
    questFields: QuestFields // Specify the type as List<MutableState<out Any>>
) {
    viewModel.selectedQuest.quest.title = questFields.title.value
    viewModel.selectedQuest.quest.description = questFields.desc.value
    viewModel.selectedQuest.quest.completed = questFields.checkedState.value
    viewModel.selectedQuest.quest.ingredients = questFields.ingredients.value
    viewModel.selectedQuest.quest.reward = questFields.reward.value
    viewModel.selectedQuest.quest.rewardxp = questFields.rewardxp.value
    viewModel.selectedQuest.quest.region = questFields.region.value
    viewModel.selectedQuest.quest.featuredImage = questFields.featuredImage.value


    viewModel.selectedQuest.quest.let { viewModel.update(it) }
    viewModel.sync()

    navigator.navigate(MyQuestsScreenDestination)
    showToast(context, "Saved " + questFields.title.value.toString())
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