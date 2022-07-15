package com.instance.dataxbranch.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.R
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.showToast
//import com.instance.dataxbranch.social.MyChatList
import com.instance.dataxbranch.social.StreamChat.MessagesActivity
import com.instance.dataxbranch.ui.viewModels.ChatRoomViewModel
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamShapes

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun RoomSelectorScreen (viewModel: UserViewModel = hiltViewModel(),
                        rviewModel: ChatRoomViewModel = hiltViewModel(),
                      // devViewModel: DevViewModel = hiltViewModel(),
                       navigator: DestinationsNavigator,
) {val db = FirebaseFirestore.getInstance()
    val me = viewModel.getMeWithAbilities()
    val context = LocalContext.current

    Scaffold(

        topBar = { DevToolbar(viewModel,navigator) },
        floatingActionButton = {}){padding ->
       // MyChatList()
ChatTheme(shapes = StreamShapes.defaultShapes().copy(
    avatar = RoundedCornerShape(8.dp),
    attachment = RoundedCornerShape(16.dp),
    myMessageBubble = RoundedCornerShape(16.dp),
    otherMessageBubble = RoundedCornerShape(16.dp),
    inputField = RectangleShape,)){
        ChannelsScreen(
            title = stringResource(id = R.string.app_name),
            isShowingSearch = true,
            onItemClick = { channel ->
                startActivity(context,MessagesActivity.createIntent(context, channel.cid),null)
               // startActivity(context,MessagesActivity.getIntent(context, channel.cid),null )
            },
            onBackPressed = { Log.d(TAG,"onBackPressed@RoomSelectorScree")
                showToast(context,"onBackPressed@RoomSelectorScreen") }
        )}
    }}