package com.instance.dataxbranch.data.firestore



import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.instance.dataxbranch.core.Constants.TITLE

import com.instance.dataxbranch.domain.Response
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

    override fun addChatRoomToFirestore(
        subject: String,
        description: String,
        author: String,
        authorid: String
    ): Flow<Response<Void?>> =flow{
        try {
            emit(Response.Loading)
            val fsid = chatRoomRef.document().id
            val thisMessage = FirestoreChatRoom(
                subject = subject,
                fsid = fsid,
                description
                = description,
                author = author,
                authorid=authorid
            )
            Log.d("FirestoreADD",thisMessage.toString())
            val addition = chatRoomRef.document(fsid).set(thisMessage).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    /*override fun addResponseToFirestore(
        uname: String, data
        : String, author: String
    ): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val id = responsesRef.document().id
            val response = Response()
            response.dateAdded= Date
            Log.d("FirestoreADD", response.toString())
            val addition = responsesRef.document(id).set(response).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }*/

    override fun addChatRoomToFirestore(room: FirestoreChatRoom): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)

            val id = if(room.fsid==""){chatRoomRef.document().id}else{room.fsid}

            val addition = id.let { chatRoomRef.document(it).set(room).await() }
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
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