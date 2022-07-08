package com.instance.dataxbranch.ui.viewModels



import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.instance.dataxbranch.data.SampleData
import com.instance.dataxbranch.data.firestore.FirestoreChatRoom
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.domain.use_case.UseCases

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    val useCases: UseCases
): ViewModel()  {

    private val _cState = mutableStateOf<Response<List<FirestoreChatRoom>>>(Response.Loading)
    val chatsState: State<Response<List<FirestoreChatRoom>>> = _cState

    private val _isChatRoomAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isChatRoomAddedState: State<Response<Void?>> = _isChatRoomAddedState

    private val _isChatRoomDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isChatRoomDeletedState: State<Response<Void?>> = _isChatRoomDeletedState
    var selectedChatRoom= FirestoreChatRoom()
    var openDialogState = mutableStateOf(false)
    private val date = SimpleDateFormat("hh:mm a")
    private val strDate: String = date.format(Date())
    private val _getSampleData = MutableLiveData<List<SampleData>>()
    val getSampleData: MutableLiveData<List<SampleData>> get() = _getSampleData
    private val _flag = mutableStateOf(false)
    val flag: Boolean get() = _flag.value

    init {
        getChatRooms()
    }

    //Static value
    private val chatListItem = mutableListOf(
        SampleData("Name 1", "Hi, Welcome", "Sample Url", strDate),
        SampleData("Name 2", "Hi, Welcome", "Sample Url", strDate),
        SampleData("Name 3", "Hi, Welcome", "Sample Url", strDate),
        SampleData("Name 4", "Hi, Welcome", "Sample Url", strDate),
        SampleData("Name 5", "Hi, Welcome", "Sample Url", strDate)
    )



    fun addChat(data: SampleData) {
        _flag.value = _flag.value != true
        chatListItem.addAll(listOf(data))
        _getSampleData.value = chatListItem
    }
    fun getConversation(): List<Pair<String, String>> {
        return selectedChatRoom.conversation
    }

    /*private fun getChatList(){
    viewModelScope.launch {
            whatsAppRepository.getChatList().collect {response->
                whatsAppStateFlow.value = response
            }
        }
        //_getSampleData.value = chatListItem
    }*/
    private fun getChatRooms() {
        viewModelScope.launch {
            useCases.getChatRooms().collect { response ->
                _cState.value = response
            }
        }
    }

    fun addChatRoom(subject: String="default", description: String="", author: String="",authorid: String="") {
        viewModelScope.launch {
            useCases.addChatRoom.invoke(subject, description, author,authorid).collect { response ->
                _isChatRoomAddedState.value = response
            }
        }
    }
}