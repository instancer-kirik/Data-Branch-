package com.instance.dataxbranch.ui

import android.content.Context

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.instance.dataxbranch.R
import com.instance.dataxbranch.data.entities.ObjectiveEntity
//import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.instance.dataxbranch.core.Constants

import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.instance.dataxbranch.ui.components.AddQuestEntityAlertDialog

import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.AddQuestEntityOnCharacterAlertDialog
import com.instance.dataxbranch.ui.components.QuestToolbar
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.instance.dataxbranch.utils.await
import kotlinx.coroutines.*

var showDialogC =     mutableStateOf(false)
var showDialogC2 =     mutableStateOf(false)
var recomposeState = mutableStateOf(false)
//viewModel: RoomQuestViewModel = hiltViewModel(),
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalStdlibApi::class)

@Destination
@Composable
fun CharacterQuestsScreen(
    //viewModel: RoomQuestViewModel = hiltViewModel(),
    uViewModel: UserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,


    ) {

    /*viewModel.rowsInserted.observeForever(){
        Log.d(TAG, "$it rows inserted")
    }*/

    //val quests= viewModel.qes.collectAsState(initial = emptyList())
    //var my_quests: StateFlow<List<QuestEntity>> = viewModel.getQuests()
    Scaffold(

        topBar = { QuestToolbar(navigator) },
        floatingActionButton = {
            AddQuestEntityFloatingActionButtonC()
        }
    ) { padding ->

        if (recomposeState.value){
            recomposeState.value =false
            }
        if (uViewModel.characterDialogState.value) {
            AddQuestEntityOnCharacterAlertDialog()
        }
        Column {
           /* Button(
                onClick = { navigator.navigate(QuestsScreenDestination) },
                modifier = Modifier.padding(2.dp)
            ) { Text("to Cloud Quests") }
*/
            Button(
                onClick = { showDialogC.value = true },
                modifier = Modifier.padding(2.dp)
            ) { Text("Button") }

            //viewModel.addQuestEntity(Quest())

            //Text(viewModel.localQuestsRepository.getQuests().forEach { it.toString() }.toString())
//viewModel.dao.getAll()
            if (showDialogC.value) {
                alertC(uViewModel,navigator)
            }
            Text("Quests are: ${uViewModel.getSelectedCharacter().character.quests} --")
            CharacterLocalLazyColumn( quests = uViewModel.getSelectedCharacter().quests, modifier = Modifier.padding(2.dp),navigator,uViewModel)
            Button(
                onClick = { showDialogC.value = true },
                modifier = Modifier.padding(2.dp)
            ) { Text("End") }

        }
    }
    }

@Composable
fun alertC(uViewModel: UserViewModel,navi: DestinationsNavigator) {
    AlertDialog(
        title = {
            Text(text = "Test")
        },
        text = {
            Text(uViewModel.getQuestsFromRepo().toString())
        },
        onDismissRequest = {

        },
        buttons = {
            Button(onClick = { showDialogC.value = false
            uViewModel.refresh(true)
            //navi.navigate(CharacterQuestsScreenDestination)

            }) {
                Text("Refresh")
            }
            Button(onClick = { CoroutineScope(Dispatchers.IO).launch { uViewModel.generalRepository.questsRepository.deleteAllRows() }}) {
                Text("Clear")
            }
        }

    )
}


@Composable
fun AddQuestEntityFloatingActionButtonC(uViewModel: UserViewModel = hiltViewModel()
) {
    FloatingActionButton(
        onClick = {
            uViewModel.characterDialogState.value = true
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = Constants.ADD_QUEST
        )
    }
}



@Composable
fun CharacterLocalLazyColumn(quests: Array<QuestWithObjectives>, modifier: Modifier,navi: DestinationsNavigator,uViewModel: UserViewModel){
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int -> selectedIndex = index
    uViewModel.selectedQuest=quests[index]
        recomposeState.value=true
    }

    Text(
        "Selected Character: ${uViewModel.getSelectedCharacter().character.name}"
                //"\n Selected Quest: ${uViewModel.selectedQuest}\"" +
                //"\n NUMQUESTS = ${uViewModel.getSelectedCharacter().quests.size}" +
                //"\n Quests: ${uViewModel.getSelectedCharacter().quests} ##"//text = "Index $index"
    )
    LazyColumn(
        modifier.fillMaxSize(),
    ) {//replace count with quest.objectives.size
        //val mObserver = Observer<List<QuestWithObjectives>> { qwe->
        itemsIndexed(quests) { index,quest ->
            //Text(quest.toString())//text = "Index $index",
            CharacterLocalQuestView(


                quest = quest,
                index = index,
                selected = selectedIndex == index,
                onClick = onItemClick,
                navi = navi,
                uViewModel = uViewModel
            )

        }
    }
    Text(
        "padding"//text = "Index $index",
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterLocalQuestView(
    navi: DestinationsNavigator,

    quest: QuestWithObjectives,
    index: Int,
    selected: Boolean,
    onClick: (Int) -> Unit,
    uViewModel: UserViewModel

) {
    //Text("DEBUG")
    Box(modifier = Modifier
        .clickable {
            onClick.invoke(index)
        }
        .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
        .fillMaxWidth()
        .padding(12.dp)) {
        Text(
            text = "Index $index",
        )
        if (quest.quest.isHabit){
            CharacterHabitCardContent(navi,quest,uViewModel)
        }else {
            CharacterLocalQuestCardContent(navi, quest, uViewModel)
        }
    }
    //Text("DEBUG2")
}

/*private suspend fun waitcomplete(username: String): Boolean {
    val shouldCreateUser = MaterialAlertDialogBuilder(this)
        .setTitle("User \"$username\" was not found.")
        .setMessage("Create new user?")
        .create()
        .await(positiveText = "Create", negativeText = "Cancel")

    if (shouldCreateUser) {
        createNewUser(username)
        return true
    }


}*/

@Composable
fun CharacterLocalQuestCardContent(navi: DestinationsNavigator, quest: QuestWithObjectives,uViewModel: UserViewModel) {

        var expanded by remember { mutableStateOf(false) }


        Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(4.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(14.dp)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                Text(text = "Title:          id= ${quest.quest.uuid}")
                Text(

                    text = quest.quest.title + "",
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                , modifier = Modifier.background(Color.Black.copy(alpha = 0.3f))
                )


                //Text(quest.objectives.toString())
                if (expanded) {

                    //var listofObjectiveEntities: Array<ObjectiveEntity> = arrayOf(ObjectiveEntity(-5,"",-5,"",Quest.ObjectiveType.Default,0,0,""))
                    //var objective: Quest.QuestObjective
                    run {
                        //Text("open")

                        quest.objectives.forEach { oe ->
                            CharacterObjectiveViewEdit(oe, uViewModel)
                        }
                        Row {
                            Button(onClick = { uViewModel.addObjectiveEntity(quest)
                            recomposeState.value=true
                            }) { Text("ADD OBJECTIVE") }
                            Button(onClick = { uViewModel.delete(quest) }) {
                                Text("Delete")
                            }
                        }

                    }
                }

            }
            Column{
            Row(
                modifier = Modifier.background(Color.Black.copy(alpha = 0.6f)),
                horizontalArrangement = Arrangement.End
            ) {
                var bouncy by remember { mutableStateOf(false) }
                val context = LocalContext.current

                val checkedState = remember { mutableStateOf(quest.quest.completed) }
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        quest.quest.apply { completed = it }
                        val result =uViewModel.onCheckboxChecked(quest, it)
                        if (result != null) { showToast(context, result) }
                    })
                //if(!checkedState.value){ trying to pop up dialog for confirm or cancel
                /*val time = measureTimeMillis {
                                runBlocking {
                                        try {
                                            showDialogC2.value = true
                                            Log.d(TAG,"IN ---------------")
                                            waitcomplete(context, quest, viewModel)
                                        }finally{
                                            Log.d(TAG,"END")
                                        }
                            }
                            }
                            showToast(context,"completed in $time millis")*/

                /*if(bouncy){checkedState.value = it
                                quest.quest.apply { completed = it }
                                viewModel.onCheckboxChecked(quest, it)}*/
                // }else{


                IconButton(onClick = {
                    expanded = !expanded
                    if (!expanded) {//saves on click when closing
                        //viewModel.addQuest(quest)
                    }
                }) {

                    Icon(
                        imageVector = if (expanded) Icons.Filled.Check else Icons.Filled.ArrowDropDown,
                        contentDescription = if (expanded) {
                            stringResource(R.string.show_less)
                        } else {
                            stringResource(R.string.show_more)
                        }

                    )

                }
            }
                Button(onClick = { navi.navigate(CharacterQuestDetailScreenDestination) }) { Text("EDIT") }

        }}
}


@Composable
fun CharacterHabitCardContent(navi: DestinationsNavigator, quest: QuestWithObjectives,uViewModel: UserViewModel) {

    var expanded by remember { mutableStateOf(false) }


    Row(horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(4.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(14.dp)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Text(text = "HABIT:          id= ${quest.quest.uuid}")
            Text(

                text = quest.quest.title + "",
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
                , modifier = Modifier.background(
                    if(quest.quest.beenAwhile()){Color.Black.copy(alpha = 0.3f)
                    }else{Color.Red.copy(alpha = 0.5f)}
                )
            )


            //Text(quest.objectives.toString())
            if (expanded) {

                //var listofObjectiveEntities: Array<ObjectiveEntity> = arrayOf(ObjectiveEntity(-5,"",-5,"",Quest.ObjectiveType.Default,0,0,""))
                //var objective: Quest.QuestObjective
                run {
                    //Text("open")

                    quest.objectives.forEach { oe ->
                        CharacterObjectiveViewEdit(oe,uViewModel)
                    }
                    Row{
                    Button(onClick = { uViewModel.addNewObjectiveEntity(quest) }) { Text("ADD OBJECTIVE") }
                    Button(onClick = { uViewModel.delete(quest) } ) {
                        Text("Delete")
                    }
                }
                }


            }

        }
        Column{
            Row(
                modifier = Modifier.background(Color.Black.copy(alpha = 0.6f)),
                horizontalArrangement = Arrangement.End
            ) {
                var bouncy by remember { mutableStateOf(false) }
                val context = LocalContext.current

                //val checkedState = remember { mutableStateOf(quest.quest.completed) }

                Button(onClick = { showToast(context,uViewModel.onHabitClick(quest))}){Text("Do")}
                //if(!checkedState.value){ trying to pop up dialog for confirm or cancel
                /*val time = measureTimeMillis {
                                runBlocking {
                                        try {
                                            showDialogC2.value = true
                                            Log.d(TAG,"IN ---------------")
                                            waitcomplete(context, quest, viewModel)
                                        }finally{
                                            Log.d(TAG,"END")
                                        }
                            }
                            }
                            showToast(context,"completed in $time millis")*/

                /*if(bouncy){checkedState.value = it
                                quest.quest.apply { completed = it }
                                viewModel.onCheckboxChecked(quest, it)}*/
                // }else{


                IconButton(onClick = {
                    expanded = !expanded
                    if (!expanded) {//saves on click when closing
                        //viewModel.addQuest(quest)
                    }
                }) {

                    Icon(
                        imageVector = if (expanded) Icons.Filled.Check else Icons.Filled.ArrowDropDown,
                        contentDescription = if (expanded) {
                            stringResource(R.string.show_less)
                        } else {
                            stringResource(R.string.show_more)
                        }

                    )

                }
            }
            Button(onClick = { navi.navigate(CharacterQuestDetailScreenDestination) }) { Text("EDIT") }
            Button(onClick = { uViewModel.delete(quest) } ) {
                Text("Delete")
            }
        }}
}
/*
@Composable
fun CompleteCheckBox(objective: Quest.QuestObjective) {
    var status by remember { mutableStateOf(objective.check) }
    IconButton(onClick = { status = !status
    }) {

        Icon(
            imageVector = if (status) Icons.Filled.Check else Icons.Default.PlayArrow,
            contentDescription = if (status) {
                stringResource(R.string.show_less)
            } else {
                stringResource(R.string.show_more)
            }

        )
    }

@Composable
    fun startObserving(viewModel: RoomQuestViewModel){viewModel.rowsInserted.observe(this, Observer {
        Log.d(TAG, "$it rows inserted");
    }


}*/

@Composable
fun CharacterObjectiveViewEdit( oe: ObjectiveEntity,uViewModel:UserViewModel) {


        var value by remember { mutableStateOf(oe.obj) }
        var expanded by remember { mutableStateOf(false) }
        var value2 by remember { mutableStateOf(oe.desc) }
        val extraPadding by animateDpAsState(
            if (expanded) 48.dp else 0.dp
        )
        Surface(
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {

            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                //Text("oid:${oe.uuid}, qid:${oe.qid}")
                OutlinedButton(
                    onClick = {
                        expanded = !expanded
                        if (!expanded) {//since save triggers expanded, handle saving here with value: String
                            //Log.d("saved the objective", "$value")
                            oe.obj =
                                value// updates quest. needs method to push back to cloud
                            oe.desc = value2
                            uViewModel.save(oe)
                        }
                    }
                ) {
                    if (expanded) {
                        Text("Save")
                        Column {
                            TextField(
                                value = "$value",
                                onValueChange = { value = it },
                                label = { Text("Edit objective") },
                                maxLines = 2,
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(8.dp)
                            )
                            TextField(
                                value = "$value2",
                                onValueChange = { value2 = it },
                                label = { Text("Edit description") },
                                maxLines = 2,
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    } else Text("Edit")
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = extraPadding)
                        .fillMaxWidth()

                ) {

                    //Text(text = "Objective ")
                    Text(text = oe.obj.toString())
                    Row(horizontalArrangement = Arrangement.End,) {
                        val checkedState = remember { mutableStateOf(oe.completed) }
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                oe.apply { completed = it }
                                uViewModel.onObjCheckedChanged(oe, it)
                            })
                    }

                }
                //CompleteCheckBox(oe) oe.apply { completed = it }
                //                        viewModel.onObjCheckedChanged(oe,it)

            }
        }
}
/*@Composable//, onClick: (Int) -> Unit
fun LocalQuestViewNoSel(viewModel: RoomQuestViewModel, quest: QuestWithObjectives,navi: DestinationsNavigator,uViewModel: UserViewModel) {
    Box(modifier = Modifier

        //.background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
        .fillMaxWidth()
        .padding(12.dp)) {
        Text(
            "placeholder2"//text = "Index $index",
        )
        LocalQuestCardContent(navi,viewModel,quest, uViewModel )
    }

}*/
suspend fun Characterwaitcomplete(context: Context, quest: QuestWithObjectives, viewModel: RoomQuestViewModel) :Boolean{
return MaterialAlertDialogBuilder(context)
.setTitle("Complete Quest?")
.create()
.await(positiveText = "Confirm", negativeText = "Cancel")
}
@Composable
fun complete2(quest: QuestWithObjectives ,viewModel: RoomQuestViewModel) :Boolean{
    if(showDialogC2.value){


    AlertDialog(
        title = {
            Text(text = "Complete Quest?")
        },
        text = {
            Text(quest.quest.title.toString())
        },
        onDismissRequest = {
            showDialogC2.value = false

        },
        buttons = {
            Button(onClick = {
                showDialogC2.value = false
                quest.quest.apply { completed = true }

            }) {
                Text("Yes")
            }
            Button(onClick = {  showDialogC2.value = false} ) {
                Text("not yet")
            }
            Button(onClick = { CoroutineScope(Dispatchers.IO).launch { viewModel.delete(quest) } }) {
                Text("No, remove quest")
            }
        }

    )}
    return quest.quest.completed
}
//private fun <T> Flow<T>.collectAsLazyPagingItems(): Any {




//inner class QuestObjectiveJson(obj: String, begin_date: String, begin_time: String,desc: String?,check: Boolean, objectiveType: Quest.ObjectiveType,requiredAmount: Int?,currentAmount: Int)


//Text("test")
//val questObjectiveJsonAdapter = QuestObjectiveJsonAdapter()
/*
val moshi: Moshi = Moshi.Builder().build()
val type: Type = Types.newParameterizedType(

Quest.QuestObjective::class.java
)
val jsonAdapter: JsonAdapter<Quest.QuestObjective> =
moshi.adapter(type)
val entityJsonAdapter: JsonAdapter<ObjectiveEntity> =
moshi.adapter(ObjectiveEntity::class.java)
val json = jsonAdapter.toJson(Quest.QuestObjective())
// ["One", "Two", "Three"]

// ["One", "Two", "Three"]

val result = jsonAdapter.fromJson(json)

*/
/*
class QuestObjectiveJsonAdapter {
@FromJson
fun fromJson(objective: Char): Quest.QuestObjective {

}

@ToJson
fun toJson(objective: Quest.QuestObjective): String? {
return objective.obj + " <" + author.email + ">"
}

}

@Json
class QuestObjectiveJson(
var obj: String,
var begin_date: String,
var begin_time: String,
var desc: String?,
var check: Boolean,
var objectiveType: Quest.ObjectiveType,
var requiredAmount: Int?,
var currentAmount: Int
) {

}

class QuestObjectiveJsonAdapter {
@FromJson
fun fromJson(questObjectiveJson: QuestObjectiveJson): Quest.QuestObjective {
    val o = Quest.QuestObjective()
    o.obj = questObjectiveJson.obj
    o.beginDateAndTime = questObjectiveJson.begin_time + questObjectiveJson.begin_date
    o.desc = questObjectiveJson.desc
    o.check = questObjectiveJson.check
    o.objectiveType = questObjectiveJson.objectiveType
    o.requiredAmount = questObjectiveJson.requiredAmount
    o.currentAmount = questObjectiveJson.currentAmount
    return o
}


@ToJson
fun toJson(questObjective: Quest.QuestObjective): QuestObjectiveJson {
    return QuestObjectiveJson(
        obj = questObjective.obj,
        begin_date = questObjective.beginDateAndTime.substring(0, 8),
        begin_time = questObjective.beginDateAndTime.substring(9, 14),
        desc = questObjective.desc,
        check = questObjective.check,
        objectiveType = questObjective.objectiveType,
        requiredAmount = questObjective.requiredAmount,
        currentAmount = questObjective.currentAmount
    )
}
*/


/* @Composable
     fun _LazyColumnWithSelection2( modifier: Modifier) {
         var selectedIndex by remember { mutableStateOf(0) }
         val onItemClick = { index: Int -> selectedIndex = index }
         val listofquests = viewModel.getAllQuestEntity().collectAsState(initial = emptyList())
         LazyColumn(
             modifier.fillMaxSize(),
         ) {//repace count with quest.objectives.size
             itemsIndexed(listofquests.value) { index, row: QuestEntity ->

                 LocalQuestView(

                     quest = row,
                     index = index,
                     selected = selectedIndex == index,
                     onClick = onItemClick
                 )
             }
         }
     }
 }
}

*/

/*
   class Card {
       public final char rank;
       public final Suit suit;

   }

   /*
   class CardAdapter {
       @ToJson fun toJson(card: Card): String {
           return card.rank + card.suit.name.substring(0, 1)
       }

       @FromJson fun fromJson(card: String): Card {
           if (card.length != 2) throw JsonDataException("Unknown card: $card")

           val rank = card[0]
           return when (card[1]) {
               'C' -> Card(rank, Suit.CLUBS)
               'D' -> Card(rank, Suit.DIAMONDS)
               'H' -> Card(rank, Suit.HEARTS)
               'S' -> Card(rank, Suit.SPADES)
               else -> throw JsonDataException("unknown suit: $card")
           }
       }
   }
   class BlackjackHand {
       public final Card hidden_card;
       public final List<Card> visible_cards;
       ...
   }


*/
   enum Suit {
       CLUBS, DIAMONDS, HEARTS, SPADES;
   }
   val objective = BlackjackHand(
       Card('6', SPADES),
       listOf(Card('4', CLUBS), Card('A', HEARTS))
   )



   val moshi = Moshi.Builder()
       .add(CardAdapter())
       .build()
   val json: String = jsonAdapter.toJson(objective)
   println(json)
   val json: String = ...

    */


//val q_objective = jsonAdapter.fromJson(json)
// print(q_objective)


/*  class QuestObjectiveJsonAdapter {
    @FromJson
    fun questObjectiveFromJson(questObjectiveJson: QuestObjectiveJson): QuestObjective {
        val questObjective = QuestObjective()
        questObjective.title = questObjectiveJson.title
        questObjective.beginDateAndTime = questObjectiveJson.begin_date.toString() + " " + questObjectiveJson.begin_time
        return questObjective
    }
    inner class QuestObjective {
        var title: String? = null
        var beginDateAndTime: String? = null
    }
    @ToJson
    fun questObjectiveToJson(questObjective: QuestObjective): QuestObjectiveJson {
        val json = QuestObjectiveJson()
        json.title = questObjective.title
        json.begin_date = questObjective.beginDateAndTime?.substring(0, 8)
        json.begin_time = questObjective.beginDateAndTime?.substring(9, 14)
        return json
    }*/
/*
class QuestObjectiveJsonAdapter {
    @FromJson
    fun questObjectiveFromJson(questObjectiveJson: QuestObjectiveJson): QuestObjective {
        val questObjective = QuestObjective()
        questObjective.title = questObjectiveJson.title
        questObjective.beginDateAndTime = questObjectiveJson.begin_date.toString() + " " + questObjectiveJson.begin_time
        return questObjective
    }
    inner class QuestObjective {
        var title: String? = null
        var beginDateAndTime: String? = null
    }
    @ToJson
    fun questObjectiveToJson(questObjective: QuestObjective): QuestObjectiveJson {
        val json = QuestObjectiveJson()
        json.title = questObjective.title
        json.begin_date = questObjective.beginDateAndTime.substring(0, 8)
        json.begin_time = questObjective.beginDateAndTime.substring(9, 14)
        return json
    }
}*/
/*
private fun <T> JsonAdapter<T>.toJson(objective: Any): String? {

}
*//*
val jsonAdapter = moshi.adapter<QuestObjective>()
    val questObjective = jsonAdapter.fromJson(json)

object json {

}
*/

/*
    val dateJson = "\"2018-11-26T11:04:19.342668Z\""
    val nullDateJson = "null"

// Hypothetical IsoDateDapter, doesn't support null by default
    val adapter: JsonAdapter<Date> = IsoDateDapter()

    val date = adapter.fromJson(dateJson)
    println(date) // Mon Nov 26 12:04:19 CET 2018

    val nullDate = adapter.fromJson(nullDateJson)
// Exception, com.squareup.moshi.JsonDataException: Expected a string but was NULL at path $

    val nullDate = adapter.nullSafe().fromJson(nullDateJson)
    println(nullDate) // null
    val cardsJsonResponse: String = "LLLL"
    class BlackjackHand(...) {
        @Json(ignore = true)
        var total: Int = 0
    }
        // We can just use a reified extension!
    val adapter = moshi.adapter<List<Card>>()
    val cards: List<Card>? = adapter.fromJson(cardsJsonResponse)
*//*
    @Retention(AnnotationRetention.RUNTIME)
    @JsonQualifier
    annotation class HexColor
    class Rectangle {
        var width = 0
        var height = 0
        @HexColor var color = 0
    }
    class Player {
        val username: String = ""

        @Json(name = "lucky number")
        val luckyNumber: Int = 0
    }
       // @SuppressWarnings("unused") // Moshi uses this!
        /** Converts strings like #ff0000 to the corresponding color ints.  */
        inner class ColorAdapter {
            @ToJson fun toJson(@HexColor rgb: Int): String {
                return "#%06x".format(rgb)
            }

            @FromJson @HexColor fun fromJson(rgb: String): Int {
                return rgb.substring(1).toInt(16)
            }
        }
        class TournamentWithNullsAdapter {
            @ToJson fun toJson(writer: JsonWriter, tournament: Tournament?,
                               delegate: JsonAdapter<Tournament?>) {
                val wasSerializeNulls: Boolean = writer.getSerializeNulls()
                writer.setSerializeNulls(true)
                try {
                    delegate.toJson(writer, tournament)
                } finally {
                    writer.setLenient(wasSerializeNulls)
                }
            }
        }

        @AlwaysSerializeNulls
        class Car(
            val make: String?,
            val model: String?,
            val color: String?
        )
        */
//printError(questsResponse.message)
/*Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val additionResponse = viewModel.isQuestAddedState.value) {
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> Unit
            is Response.Error -> Utils.printError(additionResponse.message)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val deletionResponse = viewModel.isQuestDeletedState.value) {
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> Unit
            is Response.Error -> Utils.printError(deletionResponse.message)

        }
    }*/

/* val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()
*/


