/*
package com.instance.dataxbranch.social

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.R
import com.instance.dataxbranch.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.*
import com.instance.dataxbranch.data.SampleData
import com.instance.dataxbranch.data.firestore.FirestoreChatRoom
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.ui.ObjectiveViewNoEdit
import com.instance.dataxbranch.ui.ProgressBar

import com.instance.dataxbranch.ui.QuestView
import com.instance.dataxbranch.ui.components.OnlyText
import com.instance.dataxbranch.ui.topBar
import com.instance.dataxbranch.ui.viewModels.ChatRoomViewModel
import com.instance.dataxbranch.ui.viewModels.QuestsViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
@Preview
@Composable
fun MyChatList(
    viewModel: UserViewModel = hiltViewModel(),
    cviewModel: ChatRoomViewModel = hiltViewModel(
        //factory = WhatsAppViewModelFactory(WhatsAppRepository())
    )
) {

    val menuExpanded = remember { mutableStateOf(false) }
    //val viewModel: WhatsAppChatViewModel = viewModel()
    val getAllChat = cviewModel.getSampleData

    //val chatroom = cviewModel.selectedChatRoom
    //val flag=viewModel.flag
    var OuterRoomList :List<FirestoreChatRoom> =listOf()

   // val whatsAppChatList = viewModel.whatsAppStateFlow.asStateFlow().collectAsState().value//this is basically quests list

        //cviewModel.chatsState.value




    Scaffold(
        topBar = {
            topBar(menuExpanded,cviewModel)
        },
        content = {padding->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Today, ${cviewModel.flag}", textAlign = TextAlign.Center, fontSize = 12.sp)
                Spacer(modifier = Modifier.padding(5.dp))
                //CallChatList(getAllChat.value) it's static val

                ChatListColumn(viewModel, cviewModel, cviewModel.chatRoomList,//ChatList.data,
                    modifier = Modifier.fillMaxSize().padding(padding)
                )
               */
/* when(ChatList) {

                    is Response.Loading -> ProgressBar()
                    is Response.Success -> ChatListColumn(viewModel, cviewModel, ChatList.data,//ChatList.data,
                        modifier = Modifier.fillMaxSize().padding(padding)
                    )
                    is Response.Error -> OnlyText("Error",ChatList.message)

                }*//*

            }
        },
        bottomBar = {
            BottomDesign(cviewModel)
        }
    )
}



@Composable
fun CallChatList(value: List<SampleData>) {
    val scaffoldState = rememberScaffoldState()
    Surface {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            ) {
                CallChatItem(value)
            }
        }
    }
}

@Composable
fun CallChatItem(value: List<SampleData>) {
    LazyColumn {
        items(
            value.size
        ) { index ->
            ChatListItem(data = value[index], index = index)
        }
    }
}

@Composable
fun ChatListColumn(viewModel: UserViewModel,cviewModel: ChatRoomViewModel, chatList: List<FirestoreChatRoom>, modifier: Modifier) {
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int -> selectedIndex = index}
    Text("OnIONS")
    LazyColumn(
        modifier.fillMaxSize(),
    ){//repace count with quest.objectives.size

        itemsIndexed(chatList){ index,chatRoom ->

            ChatRoomView(
                viewModel= viewModel,
                cviewModel = cviewModel,
                chatRoom=chatRoom,
                index = index,
                selected = selectedIndex== index,
                onClick = onItemClick
            )
        }
    }
    Text("END")
}
@Composable
fun ChatRoomView(viewModel: UserViewModel,cviewModel:ChatRoomViewModel, chatRoom: FirestoreChatRoom, index: Int, selected: Boolean, onClick: (Int) -> Unit) {
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
        ChatRoomCardContent(viewModel, cviewModel, chatRoom)
    }

}

@Composable
fun BottomDesign(viewModel: ChatRoomViewModel) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val scope = rememberCoroutineScope()
    val date = SimpleDateFormat("hh:mm a")
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    val strDate: String = date.format(Date())

    Row(
        modifier = Modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(0.85f)
                .wrapContentSize()
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .padding(10.dp, 0.dp, 10.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_emoji),
                contentDescription = "Emoji",
                tint = Color.Gray,
                modifier = Modifier.weight(0.1f)
            )
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                placeholder = {
                    Text(
                        text = "Message",
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                },
                modifier = Modifier
                    .weight(0.66f)
                    .wrapContentHeight(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text,
                    autoCorrect = true,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(color = Color.Black,
                    fontSize = 15.sp),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_attach),
                contentDescription = "Attach",
                tint = Color.Gray,
                modifier = Modifier.weight(0.14f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "Attach",
                tint = Color.Gray,
                modifier = Modifier.weight(0.1f)
            )
        }
        Row(
            modifier = Modifier.weight(0.15f),
            horizontalArrangement = Arrangement.Center
        ) {
            FloatingActionButton(
                onClick = {
                    if (textState.value.text.isNotEmpty()) {
                        val sampleData = SampleData(
                            "Name",
                            textState.value.text,
                            "Sample Url",
                            strDate
                        )
                        viewModel.addChat(sampleData)
                    }
                },
                backgroundColor = Color.LightGray
            ) {
                Icon(
                    painter = painterResource(
                        if (textState.value.text.isEmpty()) {
                            R.drawable.ic_voice_record
                        } else {
                            R.drawable.ic_baseline_send_24
                        }
                    ),
                    contentDescription = "Text Icon",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp)
                )

            }
        }
    }
}


@Composable
private fun ChatRoomCardContent(viewModel: UserViewModel, cviewModel: ChatRoomViewModel, chatRoom: FirestoreChatRoom) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Title: " )
            Text(
                text = chatRoom.title+"\n${chatRoom.fsid}",
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Button(onClick={*/
/*cviewModel.addChatRoom()*//*
}){
                Text("join chat room")
            }
            if (expanded) {
                Text("AAAAAAAAAMYCHATLIST" +
                        "\n${chatRoom.members}" +
                        "\n${chatRoom.subject}"+
                        "\n${chatRoom.recentMessageText}")

                //quest.objectives.forEach { objective -> ObjectiveViewNoEdit(objective = objective) }


            }
        }
        IconButton(onClick = { expanded = !expanded
            if(!expanded){//saves on click when closing
                //cviewModel.addChatRoom()
            }}) {

            Icon(
                imageVector = if (expanded) Icons.Filled.Check else Icons.Filled.ArrowDropDown,
                contentDescription = if (expanded) {
                    stringResource(com.instance.dataxbranch.R.string.show_less)
                } else {
                    stringResource(com.instance.dataxbranch.R.string.show_more)
                }

            )
        }
    }
}
@Composable
fun ChatListItem(data: SampleData, index: Int) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (index % 2 == 0) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Yellow)
                    .padding(5.dp)
            ) {
                Text(
                    text = data.desc,
                    color = Color.Black,
                    fontSize = 15.sp
                )
                Text(
                    text = data.createdDate,
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(5.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    text = data.desc,
                    color = Color.Black,
                    fontSize = 15.sp
                )
                Text(
                    text = data.createdDate,
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

*/
/*
class WhatsAppViewModelFactory(private val whatsAppRepository: WhatsAppRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WhatsAppChatViewModel::class.java)) {
            return WhatsAppChatViewModel(whatsAppRepository) as T
        }
        throw IllegalStateException()
    }
}
*//*

*/