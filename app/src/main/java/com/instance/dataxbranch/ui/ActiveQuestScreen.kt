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
import com.instance.dataxbranch.ui.components.CharacterQuestDetailToolbar
import com.instance.dataxbranch.destinations.*
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ActiveQuestScreen (viewModel: UserViewModel =hiltViewModel(),
                                navigator: DestinationsNavigator,

                                ){
    var desc = remember { mutableStateOf(viewModel.selectedQuest.quest.description + "") }

    var title = remember { mutableStateOf(viewModel.selectedQuest.quest.title + "") }
    var ingredients = remember { mutableStateOf(viewModel.selectedQuest.quest.ingredients) }
    var region = remember { mutableStateOf(viewModel.selectedQuest.quest.region) }
    //var sourceUrl = remember { mutableStateOf(viewModel.selectedQuest.quest.sourceUrl) }
    var featuredImage = remember { mutableStateOf(viewModel.selectedQuest.quest.featuredImage) }
    var reward = remember { mutableStateOf(viewModel.selectedQuest.quest.reward) }
    var isHabitState = remember { mutableStateOf(viewModel.selectedQuest.quest.isHabit) }
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
            isHabit = isHabitState
        )
    }
    val context = LocalContext.current
    Scaffold(

        topBar = { CharacterQuestDetailToolbar(context,viewModel,navigator,questFields) },
        floatingActionButton = {
            //AddQuestEntityFloatingActionButton()
            // EditQuestEntityFloatingActionButton()
        }
    ) { padding ->
        padding
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
        Row{

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
                Row {
                    Text("Completed")
                    checkedState.value?.let {
                        Checkbox(
                            checked = it,
                            onCheckedChange = {
                                checkedState.value = it
                                viewModel.selectedQuest.quest.apply { completed = it }
                                //viewModel.onCheckboxChecked(it)
                            })
                    }
                }
                Row {
                    Text("isHabit")

                    isHabitState.value?.let {
                        Checkbox(
                            checked = it,
                            onCheckedChange = {
                                isHabitState.value = it
                                viewModel.selectedQuest.quest.apply { isHabit = it }
                            })
                    }
                }
                Button(onClick = {save3(context,navigator,viewModel,questFields)}){
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = Constants.SAVE
                    )
                }
                //blvlupblock(levelups = levelup, levels = levels)
            }}}}




@Composable
fun cintBlock2(s: String = "", i: MutableState<Int>){
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

fun save3(context: Context,navigator: DestinationsNavigator,viewModel:UserViewModel,questFields: QuestFields){
//    val them =listOf(title,desc,checkedState,ingredients,reward,rewardxp,region,sourceUrl, featuredImage)
    //  val them =listOf(title,desc,checkedState,ingredients,reward,rewardxp,region,sourceUrl, featuredImage,isHabitState)
    viewModel.selectedQuest.quest.title = questFields.title.value
    viewModel.selectedQuest.quest.description = questFields.desc.value
    viewModel.selectedQuest.quest.completed = questFields.checkedState.value
    viewModel.selectedQuest.quest.ingredients = questFields.ingredients.value
    viewModel.selectedQuest.quest.reward = questFields.reward.value
    viewModel.selectedQuest.quest.rewardxp = questFields.rewardxp.value
    viewModel.selectedQuest.quest.region = questFields.region.value
    viewModel.selectedQuest.quest.featuredImage = questFields.featuredImage.value

    viewModel.selectedQuest.quest.isHabit = questFields.isHabit.value
    /*viewModel.selectedQuest.quest.
    viewModel.selectedQuest.quest.levels=levels*/
    viewModel.update(viewModel.selectedQuest)

    viewModel.sync()
    //viewModel.generalRepository.save(me.user)
    showToast(context, "Saved " + questFields.title.value)
    navigator.navigate(CharacterQuestsScreenDestination)

}

@Composable
fun alvlupblock(levels:MutableList<String>?, levelups: MutableList<Int>?){


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
                    AQuestblock(s = "", i = text2, weight = 1)

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
fun astringBlock(s: String = "", i: MutableState<String>){
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
fun AQuestblock(s:String, i:MutableState<Int>,weight: Int) {
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