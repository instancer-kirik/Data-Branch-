package com.instance.dataxbranch.ui

import android.content.Context

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.instance.dataxbranch.R
//import com.instance.dataxbranch.ui.viewModels.RoomitemViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.data.entities.ItemEntity
import com.instance.dataxbranch.ui.destinations.*
import com.instance.dataxbranch.showToast
import com.instance.dataxbranch.ui.components.AddItemEntityAlertDialog
import com.instance.dataxbranch.ui.components.DevToolbar


import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.instance.dataxbranch.utils.await
import kotlinx.coroutines.*

/*var showDialogC =     mutableStateOf(false)
var showDialogC2 =     mutableStateOf(false)
var recomposeState = mutableStateOf(false)*/
//viewModel: RoomitemViewModel = hiltViewModel(),
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalStdlibApi::class)

@Destination
@Composable
fun InventoryScreen(
//    iViewModel: ItemViewModel = hiltViewModel(),
    uViewModel: UserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,


    ) {
    val context = LocalContext.current
    /*viewModel.rowsInserted.observeForever(){
        Log.d(TAG, "$it rows inserted")
    }*/

    //val items= viewModel.qes.collectAsState(initial = emptyList())
    //var my_items: StateFlow<List<itemEntity>> = viewModel.getitems()
    Scaffold(

        topBar = { DevToolbar(uViewModel,navigator,context) },
        floatingActionButton = {
            AddItemEntityFloatingActionButton()
        }
    ) { padding ->

        if (recomposeState.value){
            recomposeState.value =false
            }
        if (uViewModel.characterDialogState.value) {
            AddItemEntityAlertDialog()
        }
        if (showDialogC.value){
            uViewModel.save()
            showToast(context,"saved selected or set popup here")
            showDialogC.value=false
        }
        Column {
           /* Button(
                onClick = { navigator.navigate(itemsScreenDestination) },
                modifier = Modifier.padding(2.dp)
            ) { Text("to Cloud items") }
*/
            Button(
                onClick = { showDialogC.value = true },
                modifier = Modifier.padding(2.dp)
            ) { Text("Button") }

            //viewModel.additemEntity(item())

            //Text(viewModel.localitemsRepository.getitems().forEach { it.toString() }.toString())
//viewModel.dao.getAll()
           /* if (showDialogC.value) {
                alertC(uViewModel,navigator)
            }*/
            Text("character is ${uViewModel.getSelectedCharacter().character.name} items are: ${uViewModel.getSelectedCharacter().character.items} --")
            InventoryGrid( them = uViewModel.getInventory()/*uViewModel.getSelectedCharacter().inventory*/, modifier = Modifier.padding(2.dp),navigator,uViewModel,context)
            Button(
                onClick = { showDialogC.value = true },
                modifier = Modifier.padding(2.dp)
            ) { Text("End") }

        }
    }
    }

/*
@Composable
fun alertC(uViewModel: UserViewModel,navi: DestinationsNavigator) {
    AlertDialog(
        title = {
            Text(text = "Test")
        },
        text = {
            Text(uViewModel.getitemsFromRepo().toString())
        },
        onDismissReitem = {

        },
        buttons = {
            Button(onClick = { showDialogC.value = false
            uViewModel.refresh(true)
            //navi.navigate(CharacteritemsScreenDestination)

            }) {
                Text("Refresh")
            }
            Button(onClick = { CoroutineScope(Dispatchers.IO).launch { uViewModel.generalRepository.itemsRepository.deleteAllRows() }}) {
                Text("Clear")
            }
        }

    )
}
*/




@Composable
fun InventoryGrid(them: Array<ItemEntity>, modifier: Modifier,navi: DestinationsNavigator,uViewModel: UserViewModel,context: Context ){
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int -> selectedIndex = index

//        iViewModel.selectedItem= them[index]
        uViewModel.setSelectI(them[index])
        recomposeState.value=true}
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        itemsIndexed(them) { ix ,item->
            InventoryItemView( item = item,
                index = ix,
                selected = selectedIndex == ix,
                onClick = onItemClick,
                navi = navi,
                uViewModel = uViewModel,
                context=context
            )
        }
    }
}

/*
@Composable
fun ItemListLocalLazyColumn(items: Array<ItemEntity>, modifier: Modifier,navi: DestinationsNavigator,uViewModel: UserViewModel){
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int -> selectedIndex = index

    }

    Text(
        "Selected Character: ${uViewModel.getSelectedCharacter().character.name}" +
                "\n Selected item: ${uViewModel.selectedInventoryItem}\"" +
                "\n inv_size = ${uViewModel.getSelectedCharacter().inventory.size} total_items = ${items.size}" +
                "\n items: ${items.toString()} ##"//text = "Index $index"
    )
    LazyColumn(
        modifier.fillMaxSize(),
    ) {//replace count with item.objectives.size
        //val mObserver = Observer<List<ItemEntity>> { qwe->
        itemsIndexed(items) { index,item ->
            //Text(item.toString())//text = "Index $index",
            InventoryItemView(


                item = item,
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
*/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InventoryItemView(
    navi: DestinationsNavigator,
    context:Context,
    item: ItemEntity,
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
        Column{
            Text(
                text = "Index $index\n name: ${item.name} id: ${item.iid}")

            InventoryItemCardContent(navi, item, uViewModel)
            Button(onClick = {
                showToast(context,item.use())
            }){Text("USE")}
            Button(onClick = {
                //uViewModel.setSelectI(item)
                uViewModel.delete(item)
            }){Text("DELETE")}
            Button(onClick = {
                uViewModel.setSelectI(item)
                navi.navigate(ItemDetailScreenDestination)
            }){Text("EDIT")}


    }}
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
fun InventoryItemCardContent(navi: DestinationsNavigator, item: ItemEntity,uViewModel: UserViewModel) {

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
//                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(14.dp)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                Text(text = "Title:          id= ${item.iid}")
                Text(

                    text = item.name + "",
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                , modifier = Modifier.background(Color.Black.copy(alpha = 0.3f))
                )


                //Text(item.objectives.toString())
                if (expanded) {
                    Text("HI 2")
                    //var listofObjectiveEntities: Array<ObjectiveEntity> = arrayOf(ObjectiveEntity(-5,"",-5,"",item.ObjectiveType.Default,0,0,""))
                    //var objective: item.itemObjective
                   /* run {
                        //Text("open")

                        *//*item.objectives.forEach { oe ->
                            CharacterObjectiveViewEdit(oe,uViewModel)
                        }*//*
                        *//*Button(onClick = { uViewModel.addNewObjectiveEntity(item) }) { Text("ADD OBJECTIVE") }*//*
                    }*/


                }

            }
            Column{
            Row(
                modifier = Modifier.background(Color.Black.copy(alpha = 0.6f)),
                horizontalArrangement = Arrangement.End
            ) {
                var bouncy by remember { mutableStateOf(false) }
                val context = LocalContext.current

                val checkedState = remember { mutableStateOf(item.has) }
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        showToast(context, "STUB")
                        /*item.apply {has  = it }
                        uViewModel.invFlux(it,item)*/
                        //WHY WOULD THIS BE IN THE INVENTORY SCREEN LOL

                        /*val result =uViewModel.onCheckboxChecked(item, it)
                        if (result != null) { showToast(context, result) }*/
                    })
                //if(!checkedState.value){ trying to pop up dialog for confirm or cancel
                /*val time = measureTimeMillis {
                                runBlocking {
                                        try {
                                            showDialogC2.value = true
                                            Log.d(TAG,"IN ---------------")
                                            waitcomplete(context, item, viewModel)
                                        }finally{
                                            Log.d(TAG,"END")
                                        }
                            }
                            }
                            showToast(context,"completed in $time millis")*/

                /*if(bouncy){checkedState.value = it
                                item.item.apply { completed = it }
                                viewModel.onCheckboxChecked(item, it)}*/
                // }else{


                IconButton(onClick = {
                    expanded = !expanded
                    if (!expanded) {//saves on click when closing
                        //viewModel.additem(item)
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
               /* Button(onClick = { *//*navi.navigate(ItemDetailScreenDestination)*//* }) { Text("EDIT") }*/
        }}
}



/*
@Composable
fun CompleteCheckBox(objective: item.itemObjective) {
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
    fun startObserving(viewModel: RoomitemViewModel){viewModel.rowsInserted.observe(this, Observer {
        Log.d(TAG, "$it rows inserted");
    }


}*/
/*
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
                OutlinedButton(
                    onClick = {
                        expanded = !expanded
                        if (!expanded) {//since save triggers expanded, handle saving here with value: String
                            //Log.d("saved the objective", "$value")
                            oe.obj =
                                value// updates item. needs method to push back to firebase
                            oe.desc = value2
                            uViewModel.update(oe)
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
}*/
/*@Composable//, onClick: (Int) -> Unit
fun LocalitemViewNoSel(viewModel: RoomitemViewModel, item: ItemEntity,navi: DestinationsNavigator,uViewModel: UserViewModel) {
    Box(modifier = Modifier

        //.background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
        .fillMaxWidth()
        .padding(12.dp)) {
        Text(
            "placeholder2"//text = "Index $index",
        )
        LocalitemCardContent(navi,viewModel,item, uViewModel )
    }

}*/

/*@Composable
fun complete2(item: ItemEntity ,viewModel: UserViewModel) :Boolean{
    if(showDialogC2.value){


    AlertDialog(
        title = {
            Text(text = "Complete item?")
        },
        text = {
            Text(item.name.toString())
        },
        onDismissRequest = {
            showDialogC2.value = false

        },
        buttons = {
            Button(onClick = {
                showDialogC2.value = false
                item.apply { completed = true }

            }) {
                Text("Yes")
            }
            Button(onClick = {  showDialogC2.value = false} ) {
                Text("not yet")
            }
            Button(onClick = { CoroutineScope(Dispatchers.IO).launch { viewModel.localitemsRepository.deleteitemEntity(item) } }) {
                Text("No, remove item")
            }
        }

    )}
    return item.item.completed
}*/
//private fun <T> Flow<T>.collectAsLazyPagingItems(): Any {




//inner class itemObjectiveJson(obj: String, begin_date: String, begin_time: String,desc: String?,check: Boolean, objectiveType: item.ObjectiveType,requiredAmount: Int?,currentAmount: Int)


//Text("test")
//val itemObjectiveJsonAdapter = itemObjectiveJsonAdapter()
/*
val moshi: Moshi = Moshi.Builder().build()
val type: Type = Types.newParameterizedType(

item.itemObjective::class.java
)
val jsonAdapter: JsonAdapter<item.itemObjective> =
moshi.adapter(type)
val entityJsonAdapter: JsonAdapter<ObjectiveEntity> =
moshi.adapter(ObjectiveEntity::class.java)
val json = jsonAdapter.toJson(item.itemObjective())
// ["One", "Two", "Three"]

// ["One", "Two", "Three"]

val result = jsonAdapter.fromJson(json)

*/
/*
class itemObjectiveJsonAdapter {
@FromJson
fun fromJson(objective: Char): item.itemObjective {

}

@ToJson
fun toJson(objective: item.itemObjective): String? {
return objective.obj + " <" + author.email + ">"
}

}

@Json
class itemObjectiveJson(
var obj: String,
var begin_date: String,
var begin_time: String,
var desc: String?,
var check: Boolean,
var objectiveType: item.ObjectiveType,
var requiredAmount: Int?,
var currentAmount: Int
) {

}

class itemObjectiveJsonAdapter {
@FromJson
fun fromJson(itemObjectiveJson: itemObjectiveJson): item.itemObjective {
    val o = item.itemObjective()
    o.obj = itemObjectiveJson.obj
    o.beginDateAndTime = itemObjectiveJson.begin_time + itemObjectiveJson.begin_date
    o.desc = itemObjectiveJson.desc
    o.check = itemObjectiveJson.check
    o.objectiveType = itemObjectiveJson.objectiveType
    o.requiredAmount = itemObjectiveJson.requiredAmount
    o.currentAmount = itemObjectiveJson.currentAmount
    return o
}


@ToJson
fun toJson(itemObjective: item.itemObjective): itemObjectiveJson {
    return itemObjectiveJson(
        obj = itemObjective.obj,
        begin_date = itemObjective.beginDateAndTime.substring(0, 8),
        begin_time = itemObjective.beginDateAndTime.substring(9, 14),
        desc = itemObjective.desc,
        check = itemObjective.check,
        objectiveType = itemObjective.objectiveType,
        requiredAmount = itemObjective.requiredAmount,
        currentAmount = itemObjective.currentAmount
    )
}
*/


/* @Composable
     fun _LazyColumnWithSelection2( modifier: Modifier) {
         var selectedIndex by remember { mutableStateOf(0) }
         val onItemClick = { index: Int -> selectedIndex = index }
         val listofitems = viewModel.getAllitemEntity().collectAsState(initial = emptyList())
         LazyColumn(
             modifier.fillMaxSize(),
         ) {//repace count with item.objectives.size
             itemsIndexed(listofitems.value) { index, row: itemEntity ->

                 LocalitemView(

                     item = row,
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


/*  class itemObjectiveJsonAdapter {
    @FromJson
    fun itemObjectiveFromJson(itemObjectiveJson: itemObjectiveJson): itemObjective {
        val itemObjective = itemObjective()
        itemObjective.title = itemObjectiveJson.title
        itemObjective.beginDateAndTime = itemObjectiveJson.begin_date.toString() + " " + itemObjectiveJson.begin_time
        return itemObjective
    }
    inner class itemObjective {
        var title: String? = null
        var beginDateAndTime: String? = null
    }
    @ToJson
    fun itemObjectiveToJson(itemObjective: itemObjective): itemObjectiveJson {
        val json = itemObjectiveJson()
        json.title = itemObjective.title
        json.begin_date = itemObjective.beginDateAndTime?.substring(0, 8)
        json.begin_time = itemObjective.beginDateAndTime?.substring(9, 14)
        return json
    }*/
/*
class itemObjectiveJsonAdapter {
    @FromJson
    fun itemObjectiveFromJson(itemObjectiveJson: itemObjectiveJson): itemObjective {
        val itemObjective = itemObjective()
        itemObjective.title = itemObjectiveJson.title
        itemObjective.beginDateAndTime = itemObjectiveJson.begin_date.toString() + " " + itemObjectiveJson.begin_time
        return itemObjective
    }
    inner class itemObjective {
        var title: String? = null
        var beginDateAndTime: String? = null
    }
    @ToJson
    fun itemObjectiveToJson(itemObjective: itemObjective): itemObjectiveJson {
        val json = itemObjectiveJson()
        json.title = itemObjective.title
        json.begin_date = itemObjective.beginDateAndTime.substring(0, 8)
        json.begin_time = itemObjective.beginDateAndTime.substring(9, 14)
        return json
    }
}*/
/*
private fun <T> JsonAdapter<T>.toJson(objective: Any): String? {

}
*//*
val jsonAdapter = moshi.adapter<itemObjective>()
    val itemObjective = jsonAdapter.fromJson(json)

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
//printError(itemsResponse.message)
/*Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val additionResponse = viewModel.isitemAddedState.value) {
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> Unit
            is Response.Error -> Utils.printError(additionResponse.message)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val deletionResponse = viewModel.isitemDeletedState.value) {
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> Unit
            is Response.Error -> Utils.printError(deletionResponse.message)

        }
    }*/

/* val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()
*/


