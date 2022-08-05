package com.instance.dataxbranch.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.data.firestore.FirestoreChatRoom
import com.instance.dataxbranch.social.StreamChat.StartupActivity
import com.instance.dataxbranch.ui.viewModels.ChatRoomViewModel
import com.instance.dataxbranch.ui.viewModels.DevViewModel
import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun RoomCreatorScreen (viewModel: UserViewModel = hiltViewModel(),
                       cViewModel: ChatRoomViewModel = hiltViewModel(),
                       devViewModel: DevViewModel = hiltViewModel(),
                       navigator: DestinationsNavigator,
) {//val db = FirebaseFirestore.getInstance()
    val me = viewModel.getMeWithAbilities()
    val context = LocalContext.current

    Scaffold(

        topBar = { DevToolbar(viewModel,navigator,context) },
        floatingActionButton = {}){
        //val addable = FirestoreChatRoom()
        //addable.members=addable.members.plus(me.user.fsid)
        val room=FirestoreChatRoom(listOf(me.user.fsid))
        Text(it.toString(),)

Button(onClick = { startActivity(context, StartupActivity.createIntent(context,""),null)
    //cViewModel.addChatRoom(room,context)
    }) {
    Text("MESSAGER")

}

    }
    //same screen to edit a chat room?
    //title
    //level barrier
    //topic
}