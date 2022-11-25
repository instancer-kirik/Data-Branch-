package com.instance.dataxbranch.ui

import android.util.Log
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
//import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.instance.dataxbranch.core.Constants
import com.instance.dataxbranch.ui.calendar.custom.DefaultMonthHeader

import com.instance.dataxbranch.ui.calendar.custom.StaticCalendar12

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

        if (recomposeState.value){
            recomposeState.value =false
            }
      /*  if (uViewModel.characterDialogState.value) {
            AddQuestEntityOnCharacterAlertDialog()
        }*/
            val stuff: Map<LocalDate, List<String>> =uViewModel.getCalendarStuff()
        Column{


            Text("$padding\n stuff is $stuff")
            CalendarContainerWithBottomSheet(uViewModel,stuff)

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
fun CalendarContainerWithBottomSheet(viewModel: UserViewModel,stuff:Map<LocalDate, List<String>> =viewModel.getCalendarStuff(),) {
    val context = LocalContext.current
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
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
                    .fillMaxSize()
                    //.height(400.dp)
                    .background(color = grey200),
                contentAlignment = Alignment.Center
            ) {
                Column{
                    Text(

                        text = "Bottom sheet",
                        fontSize = 60.sp
                    )
                    TextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                        , colors = TextFieldDefaults.textFieldColors(   backgroundColor = grey200, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent, cursorColor = Color.Black, textColor = Color.Black)
                    )
                }}
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
            val view: View = LocalView.current
Column {
    Text("Calendar")
    StaticCalendar12(modifier = Modifier, data = stuff, repo = viewModel.generalRepository)

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
}
        }
    }
}