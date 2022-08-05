package com.instance.dataxbranch.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.pager.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.R
import com.instance.dataxbranch.core.Utils
import com.instance.dataxbranch.data.SampleData
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.showToast
//import com.instance.dataxbranch.ui.destinations.QuestDetailScreenDestination
import com.instance.dataxbranch.ui.viewModels.ChatRoomViewModel
import com.instance.dataxbranch.ui.viewModels.RoomQuestViewModel

import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.instance.dataxbranch.utils.constants._tabCurrentStatus
import com.instance.dataxbranch.utils.constants.tabCurrentStatus
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalPagerApi
@Destination
@Composable
fun InRoomScreen (

    viewModel: UserViewModel = hiltViewModel(),
    cViewModel: ChatRoomViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

        val _tabCurrentStatus = MutableLiveData(0)
        val tabCurrentStatus: LiveData<Int> = _tabCurrentStatus

    /*  realtimeUpdateListener()
        buttonSendMessage.setOnClickListener{ sendMessage() }*/
    val context = LocalContext.current
        val COLLECTION_KEY = "Chat"
        val dOCUMENT_KEY = "Message"
        val NAME_FIELD = "Name"
        val TEXT_FIELD = "Text"

    val firestoreChat by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(dOCUMENT_KEY)
    }

    fun sendMessage(message: MutableState<String>,name: MutableState<String>) {
        val newMessage = mapOf(
            NAME_FIELD to name.value,
            TEXT_FIELD to message.value

        )
        firestoreChat.set(newMessage)
            .addOnSuccessListener( {
                showToast(context, "Message Sent")
            })
            .addOnFailureListener { e -> showToast(context, e.message+"") }
    }



    val db = FirebaseFirestore.getInstance()
    val me = viewModel.getMeWithAbilities()


    Scaffold(

        topBar = { DevToolbar(viewModel,navigator,context) },
        floatingActionButton = {}){padding ->

        val message = remember{ mutableStateOf("")}
        val name = remember{ mutableStateOf("")}
       // realtimeUpdateListener(firestoreChat)
        Tabs(pagerState = rememberPagerState(0))
        Button(onClick = { sendMessage(message,name) }) {Text("Send")

        }
        when (val conversation =cViewModel.getConversation()) {

            //LocalLazyColumn2(viewModel=viewModel,cviewModel=cViewModel, modifier = Modifier.padding(2.dp), navi =navigator )



        }//printError(questsResponse.message)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

        }
    }

    //title
    //host can change hot topic
    //chat


}

/*

@Composable
fun realtimeUpdateListener(firestoreChat: DocumentReference) {

    firestoreChat.addSnapshotListener { documentSnapshot, e ->
        when {
            e != null -> Log.e("ERROR", e.message+"")
            documentSnapshot != null && documentSnapshot.exists() -> {

                with(documentSnapshot) {
                    Column{
                        Text("TOP")
                    Text("${data?.get(NAME_FIELD)}:${data?.get(TEXT_FIELD)}")
                }
            }
        }
    }
}}
*/

@Composable
fun LocalLazyColumn2(
    viewModel: UserViewModel,
    cviewModel: ChatRoomViewModel,
    conversation: List<Pair<String, String>>,
    modifier: Modifier,
    navi: DestinationsNavigator,){
    var selectedIndex = remember { mutableStateOf(0) }
    val onItemClick = { index: Int -> selectedIndex.value = index}

    Text(
        "placeholder1"//text = "Index $index",
    )
    androidx.compose.foundation.lazy.LazyColumn(
        modifier.fillMaxSize(),
    ) {//replace count with quest.objectives.size
        //val mObserver = Observer<List<QuestWithObjectives>> { qwe->
        itemsIndexed(conversation) { index, line ->
            //Text(quest.toString())//text = "Index $index",
            LocalLineView(
cviewModel=cviewModel,
                viewModel = viewModel,
                line = line,
                index = index,
                selected = selectedIndex.value == index,
                onClick = onItemClick,
                navi = navi
            )

        }
    }
    Text(
        "padding"//text = "Index $index",
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocalLineView(
    navi: DestinationsNavigator,
    viewModel:UserViewModel,
    cviewModel: ChatRoomViewModel,
    line: Pair<String,String>,
    index: Int,
    selected: Boolean,
    onClick: (Int) -> Unit
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

        LocalLineCardContent(navi,viewModel,cviewModel,line)

    }
    //Text("DEBUG2")
}

@Composable
fun LocalLineCardContent(navi: DestinationsNavigator, viewModel: UserViewModel,cviewModel: ChatRoomViewModel,line: Pair<String,String>) {

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
            Text(text = "User: ")
            Text(

                text = line.first,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
                , modifier = Modifier.background(Color.Black.copy(alpha = 0.3f))
            )


            //Text(quest.objectives.toString())
            if (expanded) {
            Text(cviewModel.toString())
                //var listofObjectiveEntities: Array<ObjectiveEntity> = arrayOf(ObjectiveEntity(-5,"",-5,"",Quest.ObjectiveType.Default,0,0,""))
                //var objective: Quest.QuestObjective
                run {
                    //Text("open")

                    line.second
                    Button(onClick = { /*cviewModel.addReaction()*/}) { Text("React") }
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
/*
                val checkedState = remember { mutableStateOf(quest.quest.completed) }
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        quest.quest.apply { completed = it }
                        viewModel.onCheckboxChecked(quest, it)
                    })*/
                //if(!checkedState.value){ trying to pop up dialog for confirm or cancel
                /*val time = measureTimeMillis {
                                runBlocking {
                                        try {
                                            showDialog2.value = true
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
            //Button(onClick = { navi.navigate(QuestDetailScreenDestination) }) { Text("EDIT") }
        }
    }
}
/*
NavHost(
navController = navController,
startDestination = "whats_app_main"
) {
    composable("whats_app_main") { WhatsApp(navController) }
    composable("whats_app_chat") { WhatsAppChatList() }
}
}
}
}
}
*/

@ExperimentalPagerApi
@Composable
fun chatRoomSocial(navi: DestinationsNavigator) {
    val context = LocalContext.current
    val menuExpanded = remember { mutableStateOf(false) }
    val tabStatus = tabCurrentStatus.value

    val topBar: @Composable () -> Unit = {
        TopAppBar(
            title = {
                Text(
                    text = "ChatRooms",
                    color = Color.White,
                    fontSize = 20.sp
                )
            } ,

            actions = {
                IconButton(
                    onClick = {
                        Toast.makeText(context, "Clicked Search", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = {
                        menuExpanded.value = true
                    }
                ) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    DropdownMenu(
                        modifier = Modifier
                            .width(200.dp)
                            .wrapContentSize(Alignment.TopStart),
                        expanded = menuExpanded.value,
                        onDismissRequest = {
                            menuExpanded.value = false
                        }
                    ) {
                        when(tabStatus) {
                            0 -> {
                                DropdownMenuItem(onClick = { /*Handle New group*/ }) {
                                    Text(text = "New group")
                                }
                                DropdownMenuItem(onClick = { /*Handle New broadcast*/ }) {
                                    Text(text = "New broadcast")
                                }
                                DropdownMenuItem(onClick = { /*Handle Linked devices*/ }) {
                                    Text(text = "Linked devices")
                                }
                                DropdownMenuItem(onClick = { /*Handle Starred messages*/ }) {
                                    Text(text = "Starred messages")
                                }
                                DropdownMenuItem(onClick = { /*Handle Payments*/ }) {
                                    Text(text = "Payments")
                                }
                                DropdownMenuItem(onClick = { /*Handle Settings*/ }) {
                                    Text(text = "Settings")
                                }
                            }
                            1 -> {
                                DropdownMenuItem(onClick = { /*Handle Status privacy*/ }) {
                                    Text(text = "Status privacy")
                                }
                                DropdownMenuItem(onClick = { /*Handle Settings*/ }) {
                                    Text(text = "Settings")
                                }
                            }
                            2 -> {
                                DropdownMenuItem(onClick = { /*Handle Clear call log*/ }) {
                                    Text(text = "Clear call log")
                                }
                                DropdownMenuItem(onClick = { /*Handle Settings*/ }) {
                                    Text(text = "Settings")
                                }
                            }
                        }
                    }
                }

            },
            backgroundColor = Color.LightGray,
            elevation = AppBarDefaults.TopAppBarElevation
        )
    }

    Scaffold(
        topBar = {
            topBar()
        },
        content = {
            MyChatTab(navi)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "Message Clicked", Toast.LENGTH_SHORT).show()
                },
                backgroundColor = Color.Green,
                elevation = FloatingActionButtonDefaults.elevation(),
                modifier = Modifier.padding(10.dp)
            ) {
                when(tabStatus) {
                    0 -> {
                        Icon(
                            painterResource(id = R.drawable.ic_chat),
                            contentDescription = "Message",
                            tint = Color.White,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    1 -> {
                        Icon(
                            painterResource(id = R.drawable.ic_camera),
                            contentDescription = "Camera",
                            tint = Color.White,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    2 -> {
                        Icon(
                            painterResource(id = R.drawable.ic_call),
                            contentDescription = "Add Call",
                            tint = Color.White,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    )
}

@ExperimentalPagerApi
@Composable
fun MyChatTab(navi:DestinationsNavigator) {
    val pagerState = rememberPagerState( 0)
    Column {
        Tabs(pagerState)
        TabsContent(pagerState, navi)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("CHATS", "STATUS", "CALLS")
    val scope = rememberCoroutineScope()
    _tabCurrentStatus.value = pagerState.currentPage

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Gray,
        contentColor = Color.Gray,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 3.dp,
                color = Color.White
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        list[index],
                        color = Color.White
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

val topBar: @Composable (menuExpanded:MutableState<Boolean>,cviewModel:ChatRoomViewModel) -> Unit = {menuExpanded,cviewModel->
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back Arrow",
                    tint = Color.White
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_run_circle),
                    contentDescription = "User Image",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .padding(0.dp, 5.dp, 5.dp, 5.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "0123456789",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "online",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_videocam_24),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                menuExpanded.value = true
            }) {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "",
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier.wrapContentSize(Alignment.TopStart)
            ) {
                DropdownMenu(
                    expanded = menuExpanded.value,
                    onDismissRequest = {
                        menuExpanded.value = false
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Add to contacts")
                    }
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Report")
                    }
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Block")
                    }
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Search")
                    }
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Mute notifications")
                    }
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Wallpaper")
                    }
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "More")
                    }
                }
            }
        },
        backgroundColor = Color.LightGray,
        elevation = AppBarDefaults.TopAppBarElevation
    )
}

@Composable
fun MyChats(navi: DestinationsNavigator) {
    val date = SimpleDateFormat("hh:mm a")
    val strDate: String = date.format(Date())

    val listOfData = listOf(
        SampleData(
            "Name 1",
            "Make It Easy Sample 1",
            "Sample Url",
            strDate
        ),
        SampleData("Name 2", "Make It Easy Sample 2", "Sample Url", strDate),
        SampleData("Name 3", "Make It Easy Sample 3", "Sample Url", strDate),
        SampleData("Name 4", "Make It Easy Sample 4", "Sample Url", strDate),
        SampleData("Name 5", "Make It Easy Sample 5", "Sample Url", strDate),
        SampleData("Name 6", "Make It Easy Sample 6", "Sample Url", strDate),
        SampleData("Name 7", "Make It Easy Sample 7", "Sample Url", strDate),
        SampleData("Name 8", "Make It Easy Sample 8", "Sample Url", strDate),
        SampleData("Name 9", "Make It Easy Sample 9", "Sample Url", strDate),
        SampleData("Name 10", "Make It Easy Sample 10", "Sample Url", strDate)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier
        ) {
            items(listOfData.size) { index ->
                SampleDataListItem(listOfData[index], navi)
            }
        }
    }
}

@Composable
fun SampleDataListItem(data: SampleData, navi: DestinationsNavigator) {
    Column(
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    navi.navigate("whats_app_chat")
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_run_circle_24), //i dont have image url so i put sample in vector image
                contentDescription = "Image",
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(5.dp))
            )

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = data.name,
                        modifier = Modifier
                            .weight(1.0f),
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = data.createdDate,
                        modifier = Modifier
                            .weight(0.5f),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Text(
                    text = data.desc,
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.padding(5.dp))

                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}
@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, navi: DestinationsNavigator) {
    HorizontalPager(state = pagerState, count = 3) { page ->
        when(page) {
            0 -> MyChats(navi)
            1 -> Text("OOps")//WhatsAppStatus()
            2 ->Text("Oops2") //WhatsAppCalls()
        }
    }
}
