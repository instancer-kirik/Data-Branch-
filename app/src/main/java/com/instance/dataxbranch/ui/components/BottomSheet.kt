package com.instance.dataxbranch.ui.components

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.ui.theme.*

import kotlinx.coroutines.launch

import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
@ExperimentalMaterialApi
@Composable
fun BottomSheet (){

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    var draggableText: String = "0.0"
    val draggableState = rememberDraggableState(
        onDelta  = {delta ->



            draggableText = delta.toString()
            Log.d(TAG,draggableText)}
    )

    val coroutineScope = rememberCoroutineScope()
    val modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()//.height(200.dp)

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = modifier.weight(1F)
            ) {
                Text(text = "Hello from sheet")
            }
        }, sheetPeekHeight = 0.dp
    ) {

        Button(onClick = {
            coroutineScope.launch {

                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }, modifier = modifier.background(Color.Cyan)) {

            Column{
            Text(text = "Toggle Bottom Sheet")

        GoogleButton(imageVector = ImageVector.Builder("AA",8.dp,8.dp,1F,1F).build(), buttonText = "yooooo", backgroundColor = Color.Red, fontColor = Color.Black,
            modifier = modifier.draggable(draggableState, orientation = Orientation.Vertical, startDragImmediately = true,
                onDragStopped = { Log.d(TAG, "onDragStopped: $draggableText") },
                onDragStarted = { Log.d(TAG, "onDragStarted: $draggableText")
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {

                                bottomSheetScaffoldState.bottomSheetState.expand()

                        } else {
                            if(draggableText.toFloat() > 0){
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                                },
              )
        )
Text("move or click lol")
//GoogleButton()
    }}

}}


    @Composable
    fun GoogleButton(
        modifier: Modifier = Modifier,
        imageVector: ImageVector,
        buttonText: String,
        onClick: (isEnabled: Boolean) -> Unit = {},
        enable: Boolean = true,
        backgroundColor: Color,
        fontColor: Color,
    ) {
        Button(
            onClick = { onClick(enable) },
            modifier = modifier
                .fillMaxWidth()
                .shadow(0.dp),
                //.noInteractionClickable(enabled = false) { onClick(enable) },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            ),
            shape = RoundedCornerShape(28.dp),
            contentPadding = PaddingValues(15.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = fontColor
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = imageVector,
                        modifier = Modifier
                            .size(18.dp),
                        contentDescription = "drawable_icons",
                        tint = Color.Unspecified
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = buttonText,
                    color = MaterialTheme.colors.primaryVariant,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )



            }
        }
    }
/* a composable vertically draggable tab to open BottomSheet   */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun dragTab(bottomSheetScaffoldState: BottomSheetScaffoldState, modifier: Modifier = Modifier) {
    val draggableState = rememberDraggableState(
        onDelta  = {delta ->

            Log.d(TAG,delta.toString())}
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(20.dp)
        .background(Color.Red)
        .draggable(draggableState, orientation = Orientation.Vertical, startDragImmediately = true,
            onDragStopped = { Log.d(TAG, "onDragStopped: ") },
            onDragStarted = { Log.d(TAG, "onDragStarted: ") },
        )
    )
}
/* a composable BottomSheet that opens with a vertically draggable tab */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun dragBottomSheet(){
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    val modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()//.height(200.dp)

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = modifier.weight(1F)
            ) {
                Text(text = "Hello from sheet")
            }
        }, sheetPeekHeight = 0.dp
    ) {
        Button(onClick = {
            coroutineScope.launch {

                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }, modifier = modifier.verticalScroll(rememberScrollState())

        ){Column {
            dragTab(bottomSheetScaffoldState = bottomSheetScaffoldState)
            Text("move or click lol")
        }}}}
@Composable
fun dragBox(){
Box(modifier = Modifier.fillMaxSize()) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .background(Color.Blue)
            .size(50.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    )
}}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableSample() {
    val width = 96.dp
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.DarkGray)
        )
    }
}
/*Modifier.pointerInput(Unit) {
    detectTapGestures(
        onPress = { /* Called when the gesture starts */ },
        onDoubleTap = { /* Called on Double Tap */ },
        onLongPress = { /* Called on Long Press */ },
        onTap = { /* Called on Tap */ }
    )
}*/
@Composable
private fun ScrollBoxesSmooth() {

    // Smoothly scroll 100px on first composition
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .padding(horizontal = 8.dp)
            .verticalScroll(state)
    ) {
        repeat(10) {
            Text("Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}
@Composable
fun ScrollableSample() {
    // actual composable state
    var offset by remember { mutableStateOf(0f) }
    Box(
        Modifier
            .size(150.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                // Scrollable state: describes how to consume
                // scrolling delta and update offset
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                }
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(offset.toString())
    }
}
@Preview
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContainer() {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    val tabHeight = 36.dp
    val modifier = Modifier
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(modifier = modifier.fillMaxWidth().height(tabHeight).background(Color.Transparent)) {
                Box(
                    modifier = modifier.zIndex(4f)
                        .clip( RoundedCornerShape(tabHeight/2, tabHeight/2, 0.dp, 0.dp))
                        .width(100.dp)
                        .height(tabHeight)
                        .align(Alignment.Center)
                        .background(color = notepad)

                    ,
                    contentAlignment = Alignment.Center
                ){
                    Text("^^^",color = Color.Black)
                }
                Box(modifier = modifier.align(Alignment.TopCenter).height(tabHeight/2+2.dp).width(130.dp).background(MaterialTheme.colors.background).zIndex(1f))
            Row(modifier = modifier.clip(RoundedCornerShape(tabHeight/2, tabHeight/2, tabHeight/2, tabHeight/2)).height(tabHeight).align(alignment=Alignment.Center).background(notepad)) {

                    Box(
                    modifier = modifier
                        .size(tabHeight)
                        .clip( RoundedCornerShape(tabHeight/2,tabHeight/2,tabHeight/2,tabHeight/2))
                        .background(MaterialTheme.colors.background)
                        .zIndex(.3f))
                    Box(
                modifier = modifier.zIndex(4f)
                    .clip( RoundedCornerShape(tabHeight/2, tabHeight/2, 0.dp, 0.dp))
                    .width(100.dp)
                    .height(tabHeight)
                    .align(Alignment.CenterVertically)
                    .background(color = notepad)

                    ,
                contentAlignment = Alignment.Center
            ){
                Text("^^^",color = Color.Black)
            }
                Box(
                    modifier = Modifier
                        .size(tabHeight)
                        .clip( RoundedCornerShape(tabHeight/2,tabHeight/2,tabHeight/2,tabHeight/2,))
                        .background(MaterialTheme.colors.background)
                )
            }}
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(color = notepad),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bottom sheet",
                    fontSize = 60.sp
                )
            }
        },
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 30.dp
    ) {
        //the bottom half of the screen, covered by sheet
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    if (sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }
            }) {

                Text(text = "Bottom sheet fraction: ${sheetState.progress.fraction}")
            }
        }
    }
}