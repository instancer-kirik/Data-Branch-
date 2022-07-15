package com.instance.dataxbranch.ui

import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.data.firestore.FirestoreChatRoom
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

        topBar = { DevToolbar(viewModel,navigator) },
        floatingActionButton = {}){padding ->
        //val addable = FirestoreChatRoom()
        //addable.members=addable.members.plus(me.user.fsid)
        val room=FirestoreChatRoom(listOf(me.user.fsid))
Button(onClick = { cViewModel.addChatRoom(room,context)}) {
    Text("ADDROOM")

}

    }
    //same screen to edit a chat room?
    //title
    //level barrier
    //topic
}