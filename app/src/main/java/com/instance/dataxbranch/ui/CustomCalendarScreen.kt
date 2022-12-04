package com.instance.dataxbranch.ui

import android.util.Log
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
//import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.data.EntityType
import com.instance.dataxbranch.data.entities.NoteEntity
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.ui.calendar.custom.DayDisplayData
import com.instance.dataxbranch.ui.calendar.custom.EventCardForBottomSheet

import com.instance.dataxbranch.ui.calendar.custom.StaticCalendarForBottomSheet12
import com.instance.dataxbranch.ui.components.AddQuestEntityOnCharacterAlertDialog

import com.instance.dataxbranch.ui.components.QuestToolbar
import com.instance.dataxbranch.ui.components.getActivity
import com.instance.dataxbranch.ui.components.hideKeyboard
import com.instance.dataxbranch.ui.theme.grey200
import com.instance.dataxbranch.ui.theme.notepad
import com.instance.dataxbranch.ui.theme.purple400
import com.instance.dataxbranch.ui.viewModels.UserViewModel

import kotlinx.coroutines.*
import java.time.LocalDate


//viewModel: RoomQuestViewModel = hiltViewModel(),
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalStdlibApi::class)

@Destination
@Composable
fun CustomCalendarScreen(

    uViewModel: UserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,


    ) {
    //val modifier = Modifier.drawBehind { LinearGradient(listOf(Color.Blue,Color.LightGray))}
        Scaffold(

        topBar = { QuestToolbar(navigator) },
        floatingActionButton = {
            AddFloatingActionButtonCustomCalendar()
        }
    ) { padding ->
            if (uViewModel.characterDialogState.value) {
               EventDisplayAlertDialog()
            }
        if (recomposeState.value){
            recomposeState.value =false
            }
      /*  if (uViewModel.characterDialogState.value) {
            AddQuestEntityOnCharacterAlertDialog()
        }*/
            val stuff: Map<LocalDate, List<DayDisplayData>> =uViewModel.getCalendarStuff()
        Column{



            CalendarContainerWithBottomSheet(uViewModel,stuff)
            Text("$padding\n stuff is $stuff")
        }
    }
    }
/*
fun RegularToCustom(uViewModel:UserViewModel

): MutableMap<LocalDate, List<String>> {
    val stuff:MutableMap<LocalDate,List<String>> = mutableMapOf()
    uViewModel.getCalendarStuff().forEach{
        LocalDate.toThis(it.key)?.let { it1 -> stuff.put(it1,it.value) }
    }
    return stuff
}*/

@Composable
fun AddFloatingActionButtonCustomCalendar(uViewModel: UserViewModel = hiltViewModel()
) {
    FloatingActionButton(
        onClick = {
        TODO()
            //uViewModel.characterDialogState.value = true
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = Constants.ADD_QUEST
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarContainerWithBottomSheet(viewModel: UserViewModel, stuff:Map<LocalDate, List<DayDisplayData>> =viewModel.getCalendarStuff(),) {
    val context = LocalContext.current
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    var selectedDateStuff by remember { mutableStateOf(listOf(DayDisplayData())) }
    var noteText by remember { mutableStateOf("Hello from sheet") }
    val scope = rememberCoroutineScope()
    val tabHeight = 36.dp
    val modifier = Modifier
    if (sheetState.isAnimationRunning) {
        Log.d(Constants.TAG, "BottomSheetContainer: isAnimationRunning")
        context.getActivity()?.let { it1 -> hideKeyboard(it1) }
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(modifier = modifier
                .fillMaxWidth()
                .height(tabHeight)
                .background(Color.Transparent)) {
                Box(
                    modifier = modifier
                        .zIndex(4f)
                        .clip(RoundedCornerShape(tabHeight / 2, tabHeight / 2, 0.dp, 0.dp))
                        .width(100.dp)
                        .height(tabHeight)
                        .align(Alignment.Center)
                        .background(color = notepad)

                    ,
                    contentAlignment = Alignment.Center
                ){
                    Text("^^^",color = Color.Black)
                }
                Box(modifier = modifier
                    .align(Alignment.TopCenter)
                    .height(tabHeight / 2 + 2.dp)
                    .width(130.dp)
                    .background(MaterialTheme.colors.background)
                    .zIndex(1f))
                Row(modifier = modifier
                    .clip(
                        RoundedCornerShape(
                            tabHeight / 2,
                            tabHeight / 2,
                            tabHeight / 2,
                            tabHeight / 2
                        )
                    )
                    .height(tabHeight)
                    .align(alignment = Alignment.Center)
                    .background(notepad)) {

                    Box(
                        modifier = modifier
                            .size(tabHeight)
                            .clip(
                                RoundedCornerShape(
                                    tabHeight / 2,
                                    tabHeight / 2,
                                    tabHeight / 2,
                                    tabHeight / 2
                                )
                            )
                            .background(MaterialTheme.colors.background)
                            .zIndex(.3f))
                    Box(
                        modifier = modifier
                            .zIndex(4f)
                            .clip(RoundedCornerShape(tabHeight / 2, tabHeight / 2, 0.dp, 0.dp))
                            .width(100.dp)
                            .height(tabHeight)
                            .align(Alignment.CenterVertically)
                            .background(color = purple400)

                        ,
                        contentAlignment = Alignment.Center
                    ){
                        Text("^^^",color = Color.Black)
                    }
                    Box(
                        modifier = Modifier
                            .size(tabHeight)
                            .clip(
                                RoundedCornerShape(
                                    tabHeight / 2,
                                    tabHeight / 2,
                                    tabHeight / 2,
                                    tabHeight / 2,
                                )
                            )
                            .background(MaterialTheme.colors.background)
                    )
                }}
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(color = grey200),
                contentAlignment = Alignment.Center
            ) {/////////////////////////////////////BOTTOM SHEET////////////////////////////////////////
                Column {
                    Text(

                        text = "Bottom sheet",
                        fontSize = 20.sp,
                        modifier=Modifier.align(Alignment.CenterHorizontally)
                    )
                LazyColumn( modifier.fillMaxWidth()){

                    itemsIndexed(selectedDateStuff) { ix, item ->
                        Row {
                            Text(ix.toString())
                            EventCardForBottomSheet(event = item, onClick = {
                                viewModel.selectedDisplayData.value = item
                               viewModel.characterDialogState.value = true
                            })//viewModel.selectedEvent.value)
                            //Text(text = it.toString())
                        }
                    }
                    item{
                        TextField(
                            value = noteText,
                            onValueChange = { noteText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                            , colors = TextFieldDefaults.textFieldColors(   backgroundColor = grey200, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent, cursorColor = Color.Black, textColor = Color.Black)
                        )}}

                    }

                }

        },
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 30.dp
    ) {
        //the bottom half of the screen, covered by sheet
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter//Center
        ) {
           // val view: View = LocalView.current
Column {
    Text("Calendar")
    StaticCalendarForBottomSheet12(modifier = Modifier, data = stuff, repo = viewModel.generalRepository,
        onClick={date,eventList->
            selectedDateStuff = eventList
            noteText = eventList.toString()
            //viewModel.setCalendarStuff(date,strList)
            scope.launch {
                sheetState.expand()
            }
        }

    )

    Button(onClick = {
        if (sheetState.isCollapsed) {
            scope.launch {

                sheetState.expand()
            }
        } else {
            scope.launch {
                sheetState.collapse()


            }
            context.getActivity()?.let { it1 -> hideKeyboard(it1) }
        }

    }) {

        Text(text = "Bottom sheet fraction: ${sheetState.progress.fraction}")
    }
    Spacer(modifier = Modifier.height(50.dp))
   Column(modifier = Modifier.align(Alignment.CenterHorizontally)){
    Text(LocalDate.now().toString())
    Text("Todays events are\n ${stuff[LocalDate.now()]?.toString()}")

}
}
        }
    }
}
//has 1 event DayDisplayData(val uuid:String="", val type:Enum<EntityType> = EntityType.NONE, val text:String="")
@Composable
fun EventDisplayAlertDialog(viewModel: UserViewModel = hiltViewModel(), data :DayDisplayData = viewModel.selectedDisplayData.value
) {
    //var title by remember { mutableStateOf(data.text) }
    //val focusRequester = FocusRequester()
    val focusRequester = remember { FocusRequester() }
    if (viewModel.characterDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.characterDialogState.value = false
            },
            title = {
                Text(
                    text = Constants.ADD_QUEST
                )
            },
            text = {
                Column(modifier = Modifier.focusRequester(focusRequester)) {
                    Text(data.text)
                        //modifier = Modifier.focusRequester(focusRequester)

                    LaunchedEffect(Unit) {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    /*enum class EntityType{
    QUEST, OBJECTIVE, HABIT, NOTE, DEFAULT, NONE
}*/
                    when(data.type) {
                        EntityType.QUEST ->
                            QuestEventCard(quest = viewModel.generalRepository.questsRepository.getQuestById(data.uuid), viewModel = viewModel )
                        EntityType.NOTE ->
                            NoteEventCard(note = viewModel.generalRepository.notes.getNoteById(data.uuid), viewModel = viewModel )
                        EntityType.HABIT ->
                            HabitEventCard(habit = viewModel.generalRepository.questsRepository.getQuestById(data.uuid), viewModel = viewModel )

                        else ->
                            DefaultEventCard(event = data, viewModel = viewModel)
                            //throw UnsupportedOperationException()
                    }

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.characterDialogState.value = false
                        Log.d("AlertDialog", "Confirm")
                        //viewModel.addNewQuestEntity(title,description, author)//viewModel.selectedQuest =
                    }
                ) {
                    Text(
                        text = Constants.CLOSE
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.characterDialogState.value = false
                    }
                ) {
                    Text(
                        text = Constants.DISMISS
                    )
                }
            }
        )
    }
}

/*

usable:
    QUEST, HABIT, NOTE,  DEADLINE,

undecided:
    BIRTHDAY, ANNIVERSARY, HOLIDAY,
*/




@Composable
fun QuestEventCard(quest:QuestWithObjectives,viewModel: UserViewModel){
    Column {
        Text(""+quest.quest.title)
        Text(quest.quest.describe())
        LazyColumn(){
            itemsIndexed(quest.objectives){ix,objective->
                CharacterObjectiveView(oe =objective , uViewModel = viewModel)
                //Text(""+objective.obj)
            }
        }

    }
}
@Composable// habits are quests until I make a habit class
fun HabitEventCard(habit: QuestWithObjectives, viewModel: UserViewModel){
    Column {
        Text(""+habit.quest.title)
        Text(habit.quest.describe())
    }
}
@Composable
fun NoteEventCard(note: NoteEntity, viewModel: UserViewModel){
    Column {
        Text(""+note.title)
        Text(note.describe())

    }

}
@Composable
fun DefaultEventCard(event: DayDisplayData, viewModel: UserViewModel){
    Column {
        Text(""+event.text)
        Text(event.type.toString())
        Text(event.uuid.toString())
    }

}