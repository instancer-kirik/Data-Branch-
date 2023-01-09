package com.instance.dataxbranch.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
//import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.DayStatus
import com.instance.dataxbranch.data.EventType
import com.instance.dataxbranch.data.entities.NoteEntity
import com.instance.dataxbranch.quests.QuestWithObjectives
import com.instance.dataxbranch.ui.calendar.custom.DayData
import com.instance.dataxbranch.ui.calendar.custom.Event


import com.instance.dataxbranch.ui.calendar.custom.StaticCalendarForBottomSheet12
import com.instance.dataxbranch.ui.components.*
import com.instance.dataxbranch.ui.theme.darkGrey

import com.instance.dataxbranch.ui.theme.grey200

import com.instance.dataxbranch.ui.theme.purple400
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.smarttoolfactory.colorpicker.picker.ColorPickerRingDiamondHEX

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
        },
            bottomBar = {
                BottomBarCustomCalendar(navigator)
            }
    ) { padding ->
            val stuff: Map<LocalDate, DayData> =uViewModel.getCalendarStuff()
            if (uViewModel.characterDialogState.value) {
               EventDisplayAlertDialog()
            }
            if(uViewModel.newEventDialogState.value) {
                AddEventAlertDialog(viewModel=uViewModel,
                    onDone = {main, desc, type ->
                        when(type) {
                            EventType.NOTE -> {
                                Log.d(TAG, "CustomCalendarScreen: NOTE $main $desc")
                            }
                            EventType.HABIT-> {
                                Log.d(TAG, "CustomCalendarScreen: HABIT $main $desc")
                            }
                            EventType.QUEST -> {
                                uViewModel.addNewQuestEntity(main, desc)
                            }
                            EventType.DEFAULT -> {
                                Log.d(TAG, "CustomCalendarScreen: DEFAULT $main $desc")
                            }
                            else -> {
                                Log.d(TAG, "CustomCalendarScreen: type is null")
                            }
                        }


                    })
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
            uViewModel.newEventDialogState.value = true
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
    var selectedDateStuff by remember { mutableStateOf(listOf(Event())) }
    var noteText by remember { mutableStateOf("Hello from sheet") }
    val scope = rememberCoroutineScope()
    val tabHeight = 36.dp
    var selectedOption1 by remember {
        mutableStateOf(viewModel.getDayStatus(selectedDate.value).first)//THIS IS A STRING, NOT A DAYSTATUS
    }
    var resetStatusSelector = remember { mutableStateOf(true) }
    var expandedStatus = remember { mutableStateOf(false) }
    val modifier = Modifier
    if (sheetState.isAnimationRunning) {
        Log.d(Constants.TAG, "BottomSheetContainer: isAnimationRunning")
        context.getActivity()?.let { it1 -> hideKeyboard(it1) }
        viewModel.selectedColor.value = Color(0xFF55555E)
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
                        .clip(
                            RoundedCornerShape(
                                (tabHeight / 2) ?: 0.dp,
                                (tabHeight / 2) ?: 0.dp,
                                0.dp,
                                0.dp
                            )
                        )
                        .width(100.dp)
                        .height(tabHeight)
                        .align(Alignment.Center)
                        .background(color = darkGrey)

                    ,
                    contentAlignment = Alignment.Center
                ){
                    Text("^^^",color = Color.Cyan)
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
                            (tabHeight / 2) ?: 0.dp,
                            (tabHeight / 2) ?: 0.dp,
                            tabHeight / 2,
                            tabHeight / 2
                        )
                    )
                    .height(tabHeight)
                    .align(alignment = Alignment.Center)
                    .background(darkGrey)) {

                    Box(
                        modifier = modifier
                            .size(tabHeight)
                            .clip(
                                RoundedCornerShape(
                                    (tabHeight / 2) ?: 0.dp,
                                    (tabHeight / 2) ?: 0.dp,
                                    tabHeight / 2,
                                    tabHeight / 2
                                )
                            )
                            .background(MaterialTheme.colors.background)
                            .zIndex(.3f))
                    Box(
                        modifier = modifier
                            .zIndex(4f)
                            .clip(
                                RoundedCornerShape(
                                    (tabHeight / 2) ?: 0.dp,
                                    (tabHeight / 2) ?: 0.dp, 0.dp, 0.dp
                                )
                            )
                            .width(100.dp)
                            .height(tabHeight)
                            .align(Alignment.CenterVertically)
                            .background(color = purple400)

                        ,
                        contentAlignment = Alignment.Center
                    ){
                        Text("xxx",color = Color.Blue)
                    }
                    Box(
                        modifier = Modifier
                            .size(tabHeight)
                            .clip(
                                RoundedCornerShape(
                                    (tabHeight / 2) ?: 0.dp,
                                    (tabHeight / 2) ?: 0.dp,
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
                    .background(color = viewModel.selectedColor.value),
                contentAlignment = Alignment.Center
            ) {/////////////////////////////////////BOTTOM SHEET////////////////////////////////////////





                //I wonder if this updates when the selectedDate changes
                Column {
                    Text(

                        text = "Bottom sheet",
                        fontSize = 20.sp,
                        modifier=Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(text = "Selected date: ${selectedDate.value} Current status: $selectedOption1")
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
                    Row{Button(onClick = {
                        viewModel.openColorPickerState.value = true
                        /*scope.launch {
                            sheetState.collapse()
                        }*/
                    }) {
                        Text(text = "ColorPicker")}

                        OutlinedButton(
                            onClick = {

                                expandedStatus.value = !expandedStatus.value
                            }
                        ) {

                            if (expandedStatus.value) {

                                DayStatusSpinner(
                                    options = DayStatus.getList(),
                                    selectedOption1 = selectedOption1,
                                    onDone = {
                                        selectedOption1 = it
                                        setDayStatus(selectedDate.value, it, viewModel,)
                                        expandedStatus.value = false
                                        //resetStatusSelector.value = true
                                    },
                                reset = resetStatusSelector.value,
                                //onReset = {expanded -> expanded.value = false
                                )
                                 Log.d(TAG, "SpinnerResult: $selectedOption1")
                                /*Button(
                                    onClick = { viewModel.openDialogState2.value = true },
                                    modifier = Modifier.padding(2.dp)
                                ) { Text("Custom Status") }*/
                            }else{
                                Text("Select Status")
                            }
                        }}
                    LazyColumn( modifier.fillMaxWidth()){

                    itemsIndexed(selectedDateStuff) { ix, item ->
                        Row {
                            Text(ix.toString())

                            EventCardForBottomSheet(event = item, onClick = {
                                viewModel.selectedEvent.value = item
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
        sheetPeekHeight = 90.dp
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
        onClick={date,data->//onDateChange
            selectedDate.value=date
            selectedDateStuff = data.events
            viewModel.selectedDate.value = date
            viewModel.selectedEvent.value = data.events.firstOrNull()?: Event()
            viewModel.selectedDayData.value = data
            selectedOption1 = data.status
            noteText = data.toString()
            resetStatusSelector.value = true
            expandedStatus.value = false
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

fun setDayStatus(value: LocalDate?, option: String, viewModel:UserViewModel) {
    if (value != null) {

        viewModel.setDayStatus(value,option )
        //needs a list to store day statuses
        //iewModel.setCalendarStuff(value,option)
    }
}
fun setDayColor(value: LocalDate?, viewModel:UserViewModel,color: Color) {
    if (value != null) {

        viewModel.setDayColor(value,color)
        //needs a list to store day statuses
        //iewModel.setCalendarStuff(value,option)
    }
}

//has 1 event Event(val uuid:String="", val type:Enum<EventType> = EventType.NONE, val text:String="")
@Composable
fun EventDisplayAlertDialog(viewModel: UserViewModel = hiltViewModel(), data :Event = viewModel.selectedEvent.value
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
                    /*enum class EventType{
    QUEST, OBJECTIVE, HABIT, NOTE, DEFAULT, NONE
}*/                 Text("DDDDDDDD "+data.type.toString())
                    when(data.type) {
                        EventType.QUEST ->
                            QuestEventCard(quest = viewModel.generalRepository.questsRepository.getQuestById(data.uuid), viewModel = viewModel )
                        EventType.NOTE ->
                            NoteEventCard(note = viewModel.generalRepository.notes.getNoteById(data.uuid), viewModel = viewModel )
                        EventType.HABIT ->
                            HabitEventCard(habit = viewModel.generalRepository.questsRepository.getQuestById(data.uuid), viewModel = viewModel )

                        else ->
                            DefaultEventCard(event = data, viewModel = viewModel)
                            //throw UnsupportedOperationException()
                    }
                    Text("VVVVVVVVV")
                    Button(onClick = {
                        data.generateAndDownloadIcsFile(fileName = "event_${data.text}.ics")
                    }) {
                        Text(text = "Close")
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
                            viewModel.setDayStatus(viewModel.selectedDate.value,data.status.toString())
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
                       viewModel.setDayColor(viewModel.selectedDate.value,/*data.status.toString()*/viewModel.selectedColor.value)
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
fun DefaultEventCard(event: Event, viewModel: UserViewModel){
    Column {
        Text("X: "+event.text)
        Text(event.type.toString())
        Text(event.uuid.toString())

    }

}

@Composable
fun EventCardForBottomSheet(event:Event, onClick: (Event) -> Unit ={}){
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

@Composable
fun BottomBarCustomCalendar(navi: DestinationsNavigator){
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        cutoutShape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navi.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
            IconButton(onClick = { navi.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Box"
                )
            }
        }}
    }