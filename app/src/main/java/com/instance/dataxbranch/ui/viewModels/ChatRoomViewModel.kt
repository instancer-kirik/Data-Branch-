package com.instance.dataxbranch.ui.viewModels



import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.SampleData
import com.instance.dataxbranch.data.firestore.FirestoreChat
import com.instance.dataxbranch.data.firestore.FirestoreChatRoom
import com.instance.dataxbranch.data.firestore.SocialRepository
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.domain.use_case.UseCases
import com.instance.dataxbranch.showToast

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    val useCases: UseCases,
    val chatRoomRef: CollectionReference,

): ViewModel()  {
   // val repo: SocialRepository =getSocialRepository() is an interface. what do?
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
    val db: FirebaseFirestore= FirebaseFirestore.getInstance()
    var chatRoomList:List<FirestoreChatRoom> = listOf()

    init {
       // addChatRoom()
        getRepositoryChatRooms()
        updateChatRoomList()
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
    fun addChat(text:String){}
    fun getConversation(): List<FirestoreChat> {
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
    private fun getRepositoryChatRooms() {
        viewModelScope.launch {
            useCases.getChatRooms().collect { response ->
                _cState.value = response
            }
        }
    }
    private fun updateChatRoomList(){
        readData{roomList->
            chatRoomList=roomList
        }
    }
    fun readData(myCallback: (List<FirestoreChatRoom>) -> Unit) {
        chatRoomRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list = ArrayList<FirestoreChatRoom>()
                for (document in task.result) {
                    val room = document.toObject(FirestoreChatRoom::class.java)
                    list.add(room)
                }
                myCallback(list)
            }
        }
    }
    fun getChatRooms(): List<FirestoreChatRoom>{

        var result: List<FirestoreChatRoom> =listOf()
           db.collection("chatRooms").get().addOnCompleteListener {task->
               if (task.isSuccessful){
                    result= task.result.toObjects(FirestoreChatRoom::class.java)
                   /*for (document in task.result) {
                       val name = document.data["name"].toString()*/
                   }
               }
              //, SetOptions.merge()
               /*  .then((querySnapshot) => {
            querySnapshot.forEach((doc) => {
                console.log(`${doc.id} => ${doc.data()}`);
            });
                   */
        return result
           }
    private suspend fun getListOfChatRooms(): List<DocumentSnapshot> {
        val snapshot = chatRoomRef.get().await()
        return snapshot.documents
    }
    private suspend fun getDataFromFirestore() {
        try {
            val listOfPlaces = getListOfChatRooms()
        } catch (e: Exception) {
            Log.d(TAG,"ERROR $e") //Don't ignore potential errors!
        }
    }
/*
                .addOnSuccessListener { docref ->
                   docref.toObjects(FirestoreChatRoom::class.java)
                    Log.d(TAG,"SUCCESS GOT CHATROOMS IN VIEWMODEL ${result.toString()}")

                }
                .addOnFailureListener { e -> Log.d(TAG,"Failed to geT CHATROOMS IN VIEWMODEL $e " )
                   // result =Response.Error(e.toString())
                }*/

        /*
        Log.d(TAG,"SUCCESS1 GOT CHATROOMS IN VIEWMODEL ${result.toString()}")
        return result*/


fun addChatRoom(subject: String="default", title: String="", members: List<String> = listOf()) {
//this one doesn't work try other
viewModelScope.launch {
   useCases.addChatRoom(subject,title,members)
   /*db.collection("chatRooms")
       .add(room)//, SetOptions.merge()
       .addOnSuccessListener {docref-> showToast(context,"wrote to firestore! c;")
           _isChatRoomAddedState.value = Response.Success(null)
       }
       .addOnFailureListener { e -> showToast(context, "Error writing document $e") }*/
   //useCases.addChatRoom.invoke(title, subject, members,"","")//.collect { response ->
       _isChatRoomAddedState.value = Response.Success(null)//idk if this is the right way to do this but no red squiggles
  // }
}
}

fun addChatRoom(room: FirestoreChatRoom,context: Context) {
viewModelScope.launch {
    db.collection("chatRooms")
        .add(room)//, SetOptions.merge()
        .addOnSuccessListener { docref ->
            showToast(context, "wrote to firestore! c;")
            _isChatRoomAddedState.value = Response.Success(null)
        }
        .addOnFailureListener { e -> showToast(context, "Error writing document $e") }

/*

   useCases.addChatRoom.invoke(db.).collect { response ->
       _isChatRoomAddedState.value = response
   }*/

}}}
/*fun writeUserData(context: Context){//to firestore

db.collection("users")
   .document(fsid)
   .set(meWithAbilities.toFireStoreUser(fsid))//, SetOptions.merge()
   .addOnSuccessListener { showToast(context,"wrote to firestore! c;") }
   .addOnFailureListener { e -> showToast(context, "Error writing document $e") }

}}
*/

