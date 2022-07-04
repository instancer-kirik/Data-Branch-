package com.instance.dataxbranch.ui.viewModels



import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.firestore.FirestoreChatRoom
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.domain.use_case.UseCases

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    val useCases: UseCases
): ViewModel()  {

    private val _rState = mutableStateOf<Response<List<FirestoreChatRoom>>>(Response.Loading)
    val responseState: State<Response<List<FirestoreChatRoom>>> = _rState

    private val _isChatRoomAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isChatRoomAddedState: State<Response<Void?>> = _isChatRoomAddedState

    private val _isChatRoomDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isChatRoomDeletedState: State<Response<Void?>> = _isChatRoomDeletedState
    var selectedChatRoom= FirestoreChatRoom()
    var openDialogState = mutableStateOf(false)

    init {
        getChatRooms()
    }
    fun getConversation(): List<Pair<String, String>> {
        return selectedChatRoom.conversation
    }


    private fun getChatRooms() {
        viewModelScope.launch {
            useCases.getChatRooms().collect { response ->
                _rState.value = response
            }
        }
    }

    fun addChatRoom(subject: String, description: String, author: String,authorid: String) {
        viewModelScope.launch {
            useCases.addChatRoom.invoke(subject, description, author,authorid).collect { response ->
                _isChatRoomAddedState.value = response
            }
        }
    }
}