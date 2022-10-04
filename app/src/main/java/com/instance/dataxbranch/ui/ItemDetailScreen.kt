

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
import com.instance.dataxbranch.data.entities.ItemEntity

import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun ItemDetailScreen (
    viewModel: UserViewModel =hiltViewModel(),
//                      viewModel: ItemViewModel=hiltViewModel(),
    item:ItemEntity=viewModel.getSelectI(),
                                navigator: DestinationsNavigator,

                                ){

    /*itemToSave.name = them[0].value.toString()
    itemToSave.desc =
        them[1].value.toString()//this isn't changing even though I clearly change it.
    itemToSave.has = them[2].value as Boolean

    itemToSave.damage = them[3].value as Int
    itemToSave.color =them[4].value as String
    itemToSave.weight = them[5].value as Float
    itemToSave.note = them[6].value as String
    itemToSave.links =  them[7].value as List<String>
    itemToSave.featuredImage = them[8].value as String*/
    var desc = remember { mutableStateOf(item.desc + "") }

    var name = remember { mutableStateOf(item.name + "") }
    var damage = remember { mutableStateOf(item.damage) }
    var color = remember { mutableStateOf(item.color) }
    var links = remember { mutableStateOf(item.links) }
    var featuredImage = remember { mutableStateOf(item.featuredImage) }
    var weight = remember { mutableStateOf(item.weight) }
    //var isHabitState = remember { mutableStateOf(item.isHabit) }
    var note= remember {mutableStateOf(item.note) }
    val hasState = remember { mutableStateOf(item.has) }
    val them =listOf(name,desc,hasState,damage,color,weight,note,links, featuredImage)
    val context = LocalContext.current
    Scaffold(

        topBar = { ItemDetailToolbar(context,viewModel,navigator,them) },
        floatingActionButton = {
            //AddQuestEntityFloatingActionButton()
            // EditQuestEntityFloatingActionButton()
        }
    ) { padding ->
padding
        if (recomposeState.value){
            recomposeState.value =false
        }
       //viewModel.getSelect()

        //var Mcompleted = remember { mutableStateOf(itemToSave.completed) }

        //INTS

        //var requiredEnergy = remember { mutableStateOf(itemToSave.requiredEnergy ) }
        /*val m = mapOf("desc" to desc,

        "name" to name,
         "damage" to damage,
        "color" to color,
        "ingredients" to ingredients,
        "levels"  to levels,
        "levelup"  to levelup,
        "requiredEnergy" to requiredEnergy,)*/


        //Questblock("")
        Row{

            Column {
                Row{
                    Button(onClick={recomposeState.value=true}){Text("recompose")}

                    Button(onClick = {save3(context,navigator,viewModel,them)}){
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = Constants.SAVE
                        )
                    }}
                Text(viewModel.getSelectI().toString())
                istringBlock(s = "name", name)
                istringBlock(s = "desc", desc)
                istringBlock(s = "notes", note)
                istringBlock(s = "color", color)
                //istringBlock(s = "weight", weight)
                istringBlock(s = "featuredImage", featuredImage)

                //bintBlock(s = "damage", damage)
                iIntBlock(s = "damage", damage)


//            Column {
//                Row {
//                    Text("Obtained")
//                    Checkbox(
//                        checked =hasState.value,
//                        onCheckedChange = {status->
//                           hasState.value = status
//                           viewModel.invFlux(status ,item)
//                        })
//                }
                /*Row {
                    Text("in Inventory")

                    Checkbox(
                        checked = isHabitState.value,
                        onCheckedChange = {
                            isHabitState.value = it
                            itemToSave.apply { isHabit = it }
                        })
                }*/

                //blvlupblock(levelups = levelup, levels = levels)
            }}}}





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
                    Button(onClick = {save3(context,navigator,viewModel,them)}, modifier=Modifier.padding(2.dp)){Text("save any changes")}
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
fun iIntBlock(s: String = "", i: MutableState<Int>){
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
fun iFloatBlock(s: String = "", i: MutableState<Float>){
    var text =remember {
        mutableStateOf(i.value.toString()+"")
    }
    Surface(modifier = Modifier.width(320.dp)) {
        Text(s)
        TextField(

            value = text.value,

            onValueChange = { value ->
                if (value.length >=0) {
                    text.value = value.filter { it.isDigit()|| it=='.' }
                }
            }
        )}
}
fun save3(context: Context,navigator: DestinationsNavigator,viewModel:UserViewModel,them: List<MutableState<out Any>>){
//    val them =listOf(name,desc,checkedState,ingredients,color,colorxp,weight,sourceUrl, featuredImage)
  //  val them =listOf(name,desc,checkedState,ingredients,color,colorxp,weight,sourceUrl, featuredImage,isHabitState)
    val itemToSave = viewModel.getSelectI()


    itemToSave.name = them[0].value.toString()
    itemToSave.desc =
        them[1].value.toString()//this isn't changing even though I clearly change it.
    itemToSave.has = them[2].value as Boolean

    itemToSave.damage = them[3].value as Int
    itemToSave.color =them[4].value as String
    itemToSave.weight = them[5].value as Float
    itemToSave.note = them[6].value as String
    itemToSave.links =  them[7].value as List<String>
    itemToSave.featuredImage = them[8].value as String
    //itemToSave.isHabit = them[9].value as Boolean
    /*itemToSave.
    itemToSave.levels=levels*/
    viewModel.update(itemToSave)

    //viewModel.syncI()
    //viewModel.generalRepository.save(me.user)
    showToast(context, "Saved " + them[0].value.toString())
    viewModel.generalRepository.itemRepository.refresh()
    navigator.navigate(ItemListScreenDestination)

}
/*
@Composable
fun clvlupblock(levels:MutableList<String>?, levelups: MutableList<Int>?){


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
                    CQuestblock(s = "", i = text2, weight = 1)

                    *//*TextField(
                        value = levelups?.get(index).toString(),
                        onValueChange = { value -> levelups?.set(index, value.toInt()) },

                        maxLines = 1,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(8.dp)
                    )*//*

                }
            }
        }
    }*/



@Composable
fun istringBlock(s: String = "", i: MutableState<String>){
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
fun Itemblock(s:String, i:MutableState<Int>,weight: Int) {
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