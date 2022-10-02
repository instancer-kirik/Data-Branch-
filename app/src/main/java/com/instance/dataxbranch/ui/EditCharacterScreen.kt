package com.instance.dataxbranch.ui

import android.content.Context
import android.content.res.Configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.instance.dataxbranch.data.local.CharacterWithStuff
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.domain.getNow
import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel

import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun EditCharacterScreen(
    viewModel: UserViewModel= hiltViewModel(),
    navigator: DestinationsNavigator,
    roomQuestViewModel: RoomQuestViewModel= hiltViewModel()


    ) {
    val context = LocalContext.current
    Scaffold(

        topBar = { DevToolbar(viewModel,navigator,context) },
        floatingActionButton = {
            //AddQuestEntityFloatingActionButton()
        }
    ) { padding ->
        val configuration= LocalConfiguration.current

        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        var expanded by remember { mutableStateOf(true) }
        val backgroundColor = if (isLandscape){
            MaterialTheme.colors.secondary
        } else {
            MaterialTheme.colors.background
        }


        Spacer(Modifier.requiredHeight(20.dp))
       Row(modifier = Modifier
           .clickable {
               showToast(context, "Clicked c; in userscreen")
           }
           .background(backgroundColor)//if (selected) MaterialThec.character.colors.secondary else Color.Transparent)
           .fillMaxWidth()
           .padding(12.dp)
           )
           {
          /* val me = viewModel.getMeWithAbilities()
           me.let {
               val m = mapOf(
                   "energy " to remember { mutableStateOf(c.character.energy) },
                   "hearts " to remember { mutableStateOf(c.character.hearts) },
                   "LIFE " to remember { mutableStateOf(c.character.life) },
                   "INT " to remember { mutableStateOf(c.character.intellect) },
                   "WIS " to remember { mutableStateOf(c.character.wisdom) },
                   "CHA " to remember { mutableStateOf(c.character.charisma) },
                   "MAG " to remember { mutableStateOf(c.character.magic) },
                   "STR " to remember { mutableStateOf(c.character.strength) },
                   "STA " to remember { mutableStateOf(c.character.stamina) },
                   "AGILITY " to remember { mutableStateOf(c.character.agility) },
                   "DEX " to remember { mutableStateOf(c.character.dexterity) },
                   "SPD " to remember { mutableStateOf(c.character.speed) },
                   "CON " to remember { mutableStateOf(c.character.constitution) },
               )*/
               val c = viewModel.getSelectedCharacter()
               c.let {
                   val m = mapOf(
                       "energy " to remember { mutableStateOf(c.character.energy) },
                       "hearts " to remember { mutableStateOf(c.character.hearts) },
                       "LIFE " to remember { mutableStateOf(c.character.life) },
                       "INT " to remember { mutableStateOf(c.character.intellect) },
                       "WIS " to remember { mutableStateOf(c.character.wisdom) },
                       "CHA " to remember { mutableStateOf(c.character.charisma) },
                       "MAG " to remember { mutableStateOf(c.character.magic) },
                       "STR " to remember { mutableStateOf(c.character.strength) },
                       "STA " to remember { mutableStateOf(c.character.stamina) },
                       "AGILITY " to remember { mutableStateOf(c.character.agility) },
                       "DEX " to remember { mutableStateOf(c.character.dexterity) },
                       "SPD " to remember { mutableStateOf(c.character.speed) },
                       "CON " to remember { mutableStateOf(c.character.constitution) },
                   )
               Row{
                   Box(modifier = Modifier
                       .background(MaterialTheme.colors.surface)
                       .weight(.6f)
                       .align(Alignment.CenterVertically)){
                       CharacterSpiel(navigator,viewModel, c, roomQuestViewModel = roomQuestViewModel, m = m)
                   }

                   Box(modifier = Modifier
                       .background(MaterialTheme.colors.surface)
                       .weight(.4f)
                       .align(Alignment.CenterVertically)){

                       Row{
                           CharacterStats(m = m)
                       }
                   }
               }



           }
        }
    }
}

/*
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
                            val myfsid = viewModel.getMeWithAbilities().user.fsid
                            if( myfsid!="-1"){
                            Button(
                                onClick = {viewModel.readUserData(context, FirebaseFirestore.getInstance(),myfsid)
                                    navigator.navigate(DevScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("sync with valid fsid") }}
                            Button(
                                onClick = { navigator.navigate(DevScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("DevScreen") }
                            //Button(onClick = {
                            *//*       Button(
                                onClick = { navigator.navigate(MyQuestsScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to quests") }

                            Button(
                                onClick = { navigator.navigate(LoadoutScreenDestination) },
                                modifier = Modifier.padding(2.dp)
                            ) { Text("to loadout") }
                        }*//*
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
}*/


fun CstatState(m:Map<String, MutableState<Int>>):Map<String, Int> {
        return mapOf("energy " to m["energy "]!!.value,
        "hearts " to  m["hearts "]!!.value,
        "LIFE " to m[ "LIFE "]!!.value,
        "INT " to m["INT "]!!.value,
        "WIS " to m[   "WIS "]!!.value,
        "CHA " to m[   "CHA "]!!.value,
        "MAG " to m[   "MAG "]!!.value,
        "STR " to m[   "STR "]!!.value,
        "STA " to m[   "STA "]!!.value,
        "AGILITY " to m["AGILITY "]!!.value,
        "DEX " to m[   "DEX "]!!.value,
        "SPD " to m[   "SPD "]!!.value,
        "CON " to m[   "CON "]!!.value)
}
@Composable
fun CharacterSpiel(navigator:DestinationsNavigator, viewModel: UserViewModel, c: CharacterWithStuff,
                   m:Map<String, MutableState<Int>>, roomQuestViewModel: RoomQuestViewModel){
    var tg by remember { mutableStateOf(c.character.tagline)}
    var cn by remember { mutableStateOf(c.character.className)}
    var rc by remember { mutableStateOf(c.character.race)}
    var nm by remember { mutableStateOf(c.character.name)}
    var b by remember { mutableStateOf(c.character.bio)}
    var status by remember {mutableStateOf(c.character.status)}

    val context = LocalContext.current
    Column{
        Row {
            Button(
                onClick = {
                    val mapped = statState(m)
                    c.character.isreal = true
                    c.character.hearts = mapped["hearts "]!!

                    c.character.energy = mapped["energy "]!!
                    c.character.hearts = mapped["hearts "]!!
                    c.character.life = mapped["LIFE "]!!
                    c.character.agility = mapped["AGILITY "]!!
                    c.character.intellect = mapped["INT "]!!
                    c.character.wisdom = mapped["WIS "]!!
                    c.character.charisma = mapped["CHA "]!!
                    c.character.magic = mapped["MAG "]!!
                    c.character.strength = mapped["STR "]!!
                    c.character.stamina = mapped["STA "]!!
                    c.character.dexterity = mapped["DEX "]!!
                    c.character.speed = mapped["SPD "]!!
                    c.character.constitution = mapped["CON "]!!
                   // Log.d(TAG, "energy is now " + mapped["energy "])
                    c.character.tagline = tg
                    c.character.className = cn
                    c.character.race = rc
                    c.character.name = nm
                    c.character.bio = b
                    c.character.status = status
                    c.character.dateUpdated=getNow()
                    viewModel.save(c)
                    showToast(context = context, msg = " updated in user " + c.character)
                    var name = "init"
                    name = viewModel.refresh()
                    val prefix = ""

                    /*if(c.character.fsid!="-1"){

                        if(c.character.fsid=="-2"){
                            showToast(context = context, msg = "fsid= -2. unusual, report this to dev.")
                        }else{
                            if(c.character.cloud){viewModel.writeUserData(context,db= FirebaseFirestore.getInstance(),c.character.fsid)}
                        }
                    }*/
                    showToast(context = context, msg = " USER IS NOW $name")
                },
                modifier = Modifier.padding(2.dp)
            ) { Text("save any changes") }
            Button(onClick = {viewModel.refresh()
                navigator.navigate(UserScreenDestination)}){Text("REFRESH")
                }}
        TextField(
            value = "$rc",
            onValueChange = { rc = it },
            //label = { Text("tagline:") },
            maxLines = 2,
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = "$cn",
            onValueChange = { cn = it },
            //label = { Text("tagline:") },
            maxLines = 2,
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = "$nm",
            onValueChange = { nm = it },
           // label = { Text("tagline:") },
            maxLines = 2,
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp)
        )

        TextField(
            value = "$b",
            onValueChange = { b = it },
            //label = { Text("tagline:") },
            maxLines = 2,
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = "$tg",
            onValueChange = { tg = it },
            label = { Text("tagline:") },
            maxLines = 2,
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = "$status",
            onValueChange = { status = it },
            label = { Text("status:") },
            maxLines = 2,
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp)
        )
        //stringblock("fsid: " ,c.character.fsid +"")
        stringblock("active/selected quest: " ,   roomQuestViewModel.selectedQuest(c.character.getActiveQuest()))
        stringblock("rating: " ,c.character.rating.toString() +"/"+ c.character.rating_denominator.toString())
        //stringblock("id:  " ,c.character.id.toString())
        stringblock("uuid: " ,c.character.uuid.toString())

        stringblock("level: " ,c.character.level.toString())

    }
}
/*fun loadCompleted(    db: FirebaseFirestore
){
    var completed = []
    db.collection("users").doc(_auth.currentUser.fsid)
        .get()
        .then((snapshot){
            for (quest in snapshot.data()["qCompleted"]){
                setState(() {
                completed.add(quest)
                )}
            }
        })
}

    @Composable
    fun LazyColumn(viewModel: UserViewModel, modifier: Modifier){// questsResponse: List<Quest>
        var selectedIndex by remember { mutableStateOf(0) }
        val onItemClick = { index: Int -> selectedIndex = index}
        Column(
            modifier.fillMaxSize(),
        ){/*replace count with quest.objectives.size
            itemsIndexed(questsResponse){ index,quest ->

                QuestView(
                    viewModel= viewModel,
                    quest = quest,
                    index = index,
                    selected = selectedIndex == index,
                    onClick = onItemClick
                )
            }
        }
    }*/*/

@Composable
fun CharacterStats(m: Map<String, MutableState<Int>>){
    Column(modifier=Modifier.fillMaxWidth()){

        //Text("My stats. ")

        m.forEach{statblock(it.key,it.value)}
    }

}
/*

@Composable
fun statblock(s:String, i:MutableState<Int>,weight: Int=1){
    val context = LocalContext.current
    Row( modifier = Modifier
        .padding(2.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(backgroundColor)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceEvenly){


        Box(modifier = Modifier.width(40.dp).height(40.dp).background(Yellow).weight(1f).align(Alignment.CenterVertically).clickable {
            i.value = i.value-1*weight
            showToast(context = context, msg ="reduced to $i"  )}){
            Spacer(modifier = Modifier
                .padding(12.dp))
            Text("     _      ",modifier=Modifier.background(Black.copy(alpha = 0.6f)))
        }
        Text(s + i.value,modifier=Modifier.background(Black.copy(alpha = 0.6f)))

        Box(modifier = Modifier.width(40.dp).height(40.dp).background(Black).weight(0.5f).align(Alignment.CenterVertically).clickable {
            i.value = i.value+1*weight
            showToast(context = context, msg ="increased to $i" )}){
            Spacer(modifier = Modifier
                .padding(20.dp))
            Text("+",modifier=Modifier.background(Black.copy(alpha = 0.6f)))
        }

}}
@Composable
fun stringblock(s:String, i:String){

    Text(s + i)
}
*/

/*
uname
name
bio
tagline
fsid
activeQuest
rating
rating_denominator
me_id
uid
level
energy
hearts
life
intellect
wisdom
charisma
magic
strength
stamina
agility
dexterity
speed
constitution*/