package com.instance.dataxbranch.ui

import android.util.Log
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.instance.dataxbranch.data.DayStatus
import com.instance.dataxbranch.data.EntityType
import com.instance.dataxbranch.data.entities.NoteEntity
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.ui.calendar.custom.DayData
import com.instance.dataxbranch.ui.calendar.custom.DayDisplayData


import com.instance.dataxbranch.ui.calendar.custom.StaticCalendarForBottomSheet12
import com.instance.dataxbranch.ui.components.*

import com.instance.dataxbranch.ui.theme.grey200
import com.instance.dataxbranch.ui.theme.notepad
import com.instance.dataxbranch.ui.theme.purple400
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.smarttoolfactory.colorpicker.picker.ColorPickerRingDiamondHEX
import com.smarttoolfactory.colorpicker.picker.ColorPickerRingRectHSL

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
            val stuff: Map<LocalDate, DayData> =uViewModel.getCalendarStuff()
            if (uViewModel.characterDialogState.value) {
               EventDisplayAlertDialog()
            }
            if(uViewModel.openDialogState.value) {
                AddNoteEntityAlertDialog()
            }
            if(uViewModel.openColorPickerState.value) {
                ColorPickerAlertDialog()
            }
        if (recomposeState.value){
            recomposeState.value =false
            }
      /*  if (uViewModel.characterDialogState.value) {
            AddQuestEntityOnCharacterAlertDialog()
        }*/

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
        uViewModel.openDialogState.value = true
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
fun CalendarContainerWithBottomSheet(viewModel: UserViewModel, stuff:Map<LocalDate, DayData> ) {
    val context = LocalContext.current
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val selectedDate = remember { mutableStateOf(viewModel.selectedDate.value) }
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
                    .height(500.dp)
                    .background(color = Color.Black),
                contentAlignment = Alignment.Center
            ) {/////////////////////////////////////BOTTOM SHEET////////////////////////////////////////
                var selectedOption1 by remember {
                    mutableStateOf(viewModel.getDayStatus(selectedDate.value))//THIS IS A STRING, NOT A DAYSTATUS
                }//I wonder if this updates when the selectedDate changes
                Column {
                    Text(

                        text = "Bottom sheet",
                        fontSize = 20.sp,
                        modifier=Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(text = "Selected date: ${selectedDate.value}")
                    //displays status selector

                    /*MultiSelector(
                        options = DayStatus.getList(),
                        selectedOption = selectedOption1,
                        onOptionSelect = { option ->
                            selectedOption1 = option
                            setDayStatus(selectedDate.value,option,viewModel)

                            //also figure what to do about colors
                        },
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                            .height(56.dp)
                    )*/
                    Button(onClick = {
                        viewModel.openColorPickerState.value = true
                        /*scope.launch {
                            sheetState.collapse()
                        }*/
                    }) {
                        Text(text = "ColorPicker")
                    }
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
        onClick={date,data->
            selectedDate.value=date
            selectedDateStuff = data.displayData
            viewModel.selectedDate.value = date
            viewModel.selectedDisplayData.value = data.displayData.firstOrNull()?: DayDisplayData()
            viewModel.selectedDayData.value = data

            noteText = data.toString()
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
    //Text(LocalDate.now().toString())
    //Text("Todays events are\n ${stuff[LocalDate.now()]?.toString()}")
       Text("Selected Color: ${viewModel.selectedColor.value}")
       Text("Selected Day's color: ${viewModel.selectedDayData.value.color}")
}
}
        }
    }
}

fun setDayStatus(value: LocalDate?, option: String, viewModel:UserViewModel,color: Color) {
    if (value != null) {

        viewModel.setDayStatus(value,option,color)
        //needs a list to store day statuses
        //iewModel.setCalendarStuff(value,option)
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
                    text = Constants.OK
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
                    Spacer(modifier = Modifier.height(2.dp))
                    /*enum class EntityType{
    QUEST, OBJECTIVE, HABIT, NOTE, DEFAULT, NONE
}*/                 Text("DDDDDDDD "+data.type.toString())
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
                    Text("VVVVVVVVV")
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
@Composable
fun ColorPickerAlertDialog(viewModel: UserViewModel = hiltViewModel(), data :DayData = viewModel.selectedDayData.value
) {
    //var title by remember { mutableStateOf(data.text) }
    //val focusRequester = FocusRequester()
    val focusRequester = remember { FocusRequester() }
    if ( viewModel.openColorPickerState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.openColorPickerState.value= false
            },
            title = {
                Text(
                    text = Constants.OK
                )
            },
            text = {
                Column(modifier = Modifier.focusRequester(focusRequester)) {
                    Text(data.status.toString())
                    //modifier = Modifier.focusRequester(focusRequester)

                    LaunchedEffect(Unit) {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))

                    //Text("DDDDDDDD ")
                    ColorPickerRingDiamondHEX(
                        initialColor= viewModel.selectedDayData.value.color,
                        ringOuterRadiusFraction = .9f,
                        ringInnerRadiusFraction= .6f,
                        ringBackgroundColor = Color.Transparent,
                        ringBorderStrokeColor = Color.Black,
                        ringBorderStrokeWidth= 4.dp,
                        selectionRadius= 8.dp,
                        onColorChange = { color,hex->
                            Log.d(Constants.TAG, "CalendarContainerWithBottomSheet: onColorChange: $color \n HEX: $hex")
                            viewModel.setDayStatus(viewModel.selectedDate.value,data.status.toString(),color)
                            viewModel.selectedDayData.value.color=color
                            viewModel.selectedColor.value = color
                        },
                    )
                    //Text("VVVVVVVVV")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                       viewModel.setDayStatus(viewModel.selectedDate.value,data.status.toString(),viewModel.selectedColor.value)
                        viewModel.openColorPickerState.value = false
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
                        viewModel.openColorPickerState.value = false
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



//////////////////////////////////////////////THESE ARE IN THE ALERT DIALOG////////////////////////////////////////
@Composable
fun QuestEventCard(quest:QuestWithObjectives,viewModel: UserViewModel){
    Column {
        Text("Q: "+quest.quest.title)
        Text(quest.quest.describe())
        Text("id: "+quest.quest.uuid)
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
        Text("H: "+habit.quest.title)
        Text(habit.quest.describe())
        Text("id: "+habit.quest.uuid)
    }
}
@Composable
fun NoteEventCard(note: NoteEntity, viewModel: UserViewModel){
    Column {
        Text("N: "+note.title)
        Text(note.describe())
        Text("id: "+note.uuid)

    }

}
@Composable
fun DefaultEventCard(event: DayDisplayData, viewModel: UserViewModel){
    Column {
        Text("X: "+event.text)
        Text(event.type.toString())
        Text(event.uuid.toString())

    }

}

@Composable
fun EventCardForBottomSheet(event:DayDisplayData, onClick: (DayDisplayData) -> Unit ={}){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(onClick = { onClick.invoke(event) }),
        elevation = 4.dp,
        //shape = RoundedCornerShape(8.dp)
    ) {
        Row{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Text(text = event.text, style = MaterialTheme.typography.h6)
                Text(text = event.type.toString(), style = MaterialTheme.typography.body2)
                Text(text = "uuid: ${event.uuid}", style = MaterialTheme.typography.body2)
            }
            Text(text = "invisible2")

        }

    }
}