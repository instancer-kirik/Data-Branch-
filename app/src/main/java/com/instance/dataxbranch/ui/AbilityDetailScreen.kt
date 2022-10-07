package com.instance.dataxbranch.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.AbilityDetailToolbar
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AbilityDetailScreen (viewModel: UserViewModel=hiltViewModel(),
                         navigator: DestinationsNavigator,

){
    val context = LocalContext.current
    Scaffold(

        topBar = { AbilityDetailToolbar(context,viewModel,navigator) },
        floatingActionButton = {
            //AddAbilityEntityFloatingActionButton()
            // EditAbilityEntityFloatingActionButton()
        }
    ) { padding ->
        viewModel.getSelect()

    var desc=remember { mutableStateOf(viewModel.selectedAE.desc +"") }

    var title = remember { mutableStateOf(viewModel.selectedAE.title +"") }
    var damage = remember { mutableStateOf(viewModel.selectedAE.damage) }
    var cooldown =remember { mutableStateOf(viewModel.selectedAE.cooldown ) }
    var castTime = remember { mutableStateOf(viewModel.selectedAE.castTime ) }
    var levels = remember { viewModel.selectedAE.levels.toMutableList() }
    var levelup= remember {viewModel.selectedAE.levelup.toMutableList() }
    var requiredEnergy = remember { mutableStateOf(viewModel.selectedAE.requiredEnergy ) }
        /*val m = mapOf("desc" to desc,

        "title" to title,
         "damage" to damage,
        "cooldown" to cooldown,
        "castTime" to castTime,
        "levels"  to levels,
        "levelup"  to levelup,
        "requiredEnergy" to requiredEnergy,)*/
        val checkedState = remember { mutableStateOf(viewModel.selectedAE.inloadout?:false) }

        //abilityblock("")
        Row{

Column {
    stringBlock(s = "notes", desc)
    stringBlock(s = "title", title)
    intBlock(s = "damage", damage)
    stringBlock(s = "castTime", castTime)
    stringBlock(s = "cooldown", cooldown)
    intBlock(s = "requiredEnergy", requiredEnergy)
}
            Column {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        viewModel.selectedAE.apply { inloadout = it }
                        //viewModel.onCheckboxChecked(it)
                    })

                Button(onClick = {viewModel.selectedAE.desc=desc.value//this isn't changing even though I clearly change it.
                    viewModel.selectedAE.title =title.value
                    viewModel.selectedAE.damage= damage.value
                    viewModel.selectedAE.castTime = castTime.value
                    viewModel.selectedAE.cooldown = cooldown.value
                    viewModel.selectedAE.requiredEnergy = requiredEnergy.value
                    viewModel.selectedAE.levelup=levelup
                    viewModel.selectedAE.levels=levels
                    viewModel.selectedAE.let { viewModel.update(it) }

                    viewModel.sync()
                    //viewModel.generalRepository.save(me.user)

                    navigator.navigate(AbilitiesScreenDestination)
                showToast(context,"Saved " +desc.value)}){
                        Icon(
                        imageVector = Icons.Default.Done,
                    contentDescription = Constants.SAVE
                )
                }
                lvlupblock(levelups = levelup, levels = levels)
            }}}}

@Composable
fun intBlock(s: String = "", i: MutableState<Int?>){
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
fun lvlupblock(levels:MutableList<String>?, levelups: MutableList<Int>?){


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
                    abilityblock(s = "", i = text2, weight = 1)

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
fun stringBlock(s: String = "", i: MutableState<String>){
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
fun abilityblock(s:String, i:MutableState<Int>,weight: Int) {
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