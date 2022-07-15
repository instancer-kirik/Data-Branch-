package com.instance.dataxbranch.data.firestore



import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.core.Constants.TITLE

import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.showToast
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class SocialRepositoryImpl @Inject constructor(
    override var chatRoomRef: CollectionReference
): SocialRepository {


    override fun getChatRoomById(fsid: String):Flow<Response<FirestoreChatRoom>> = callbackFlow {
        val snapshotListener = chatRoomRef.document(fsid).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val Rresponse = snapshot.toObject(FirestoreChatRoom::class.java)!!
                Response.Success(Rresponse)
            } else {
                Response.Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getChatRoomsFromFirestore(): Flow<Response<List<FirestoreChatRoom>>> = callbackFlow {
        val snapshotListener = chatRoomRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val responses = snapshot.toObjects(FirestoreChatRoom::class.java)
                Response.Success(responses)
            } else {
                Response.Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun addMessageToFirestoreChatRoom(
        firestoreChatRoom:FirestoreChatRoom,
        text: String,
        name: String,
        imgUrl: String,

    ){//}: Flow<Response<Void?>> =flow{
        val createdDate: String = firestoreChatRoom.getNow()
        try {
            //emit(Response.Loading)
            //val fsid = chatRoomRef.document().id
            val thisMessage = FirestoreChat(name, text, imgUrl, createdDate,firestoreChatRoom.subject)

            //var subject: String,firestoreChatRoom.subject, text,name, imgUrl,createdDate)
            Log.d("FirestoreADD",thisMessage.toString())
            val addition = chatRoomRef.document(firestoreChatRoom.fsid).collection("conversation").add(thisMessage)
                .addOnSuccessListener { Log.d(TAG,"SENT IN REP IMPL") }
                .addOnFailureListener { Log.d(TAG,"dud")}
           // emit(Response.Success(addition))
        } catch (e: Exception) {
          // emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun addChatRoomToFirestore(
        title: String,
        subject: String,
        members: List<String>,
/*
        subject: String,
        description: String,
        author: String,
        authorid: String*/
    ){//}: Flow<Response<Void?>> = flow {
        val newRoom =  FirestoreChatRoom(title=title,
            subject=subject,
            members=members,
        )
        /*try {
            emit(Response.Loading)
            //val id =newRoom.fsid

            val addition= chatRoomRef.add(newRoom).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }*/
        try {
           // emit(Response.Loading)
            //val id = chatRoomRef.document().id
            //Log.d("FirestoreADD",quest.toString())
            val addition = chatRoomRef.add(newRoom)
                .addOnSuccessListener {docref-> Log.d(TAG,"wrote to firestore! c; $docref")
                // _isChatRoomAddedState.value = response
            }
                .addOnFailureListener { e -> Log.d(TAG,"Error writing document $e") }
           // emit(Response.Success(addition))
        } catch (e: Exception) {
           // emit(Response.Error(e.message ?: e.toString()))
        }
    }
   /* fun saveMessage(messageText, sentAt, currentGroupId) {
        if (messageText.trim()) {
            const message = {
                messageText,
                sentAt,
                sentBy: this.user.uid,
            }
            return Promise((resolve, reject) => {
                db.collection('message')
                    .doc(currentGroupId)
                    .collection('messages')
                    .add(message)
                    .then(function (docRef) {
                        resolve(message)
                    })
                    .catch(function (error) {
                        reject(error)
                    })
            })
        }
    },*/

    /*override fun addResponseToFirestore(
        uname: String, data
        : String, author: String
    ): Flow<Response<Void?>>
    }*/

    override fun addChatRoomToFirestore(room: FirestoreChatRoom){//: Flow<Response<Void?>> = flow {
        chatRoomRef
            .add(room)
            .addOnSuccessListener { Log.d(TAG, "addchatroom to firestore!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        /*try {
            emit(Response.Loading)

            val id = if(room.fsid==""){chatRoomRef.document().id}else{room.fsid}

            val addition = id.let { chatRoomRef.document(it).set(room).await() }
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }*/
    }


    override fun deleteChatRoomFromFirestore(fsid: String): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val deletion = chatRoomRef.document(fsid).delete().await()
            emit(Response.Success(deletion))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    /*override fun addLocalResponseToFirestore(response: com.instance.dataxbranch.data.entities.Response) = flow {
        try {
            emit(Response.Loading)
            val id = response.fsid

            val addition = id?.let { responsesRef.document(it).set(response).await() }
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }*/
}