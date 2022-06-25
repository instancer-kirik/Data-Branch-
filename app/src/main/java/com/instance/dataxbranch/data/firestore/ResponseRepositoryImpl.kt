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
class ResponseRepositoryImpl @Inject constructor(
        override var responsesRef: CollectionReference
    ): ResponseRepository {


        override fun getResponseById(fsid: String):Flow<Response<FirestoreResponse>> = callbackFlow {
            val snapshotListener = responsesRef.document(fsid).addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val Rresponse = snapshot.toObject(FirestoreResponse::class.java)!!
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

        override fun getResponsesFromFirestore(): Flow<Response<List<FirestoreResponse>>> = callbackFlow {
            val snapshotListener = responsesRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val responses = snapshot.toObjects(FirestoreResponse::class.java)
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

    override fun addResponseToFirestore(
        subject: String,
        description: String,
        author: String,
        authorid: String
    ): Flow<Response<Void?>> =flow{
        try {
            emit(Response.Loading)
            val fsid = responsesRef.document().id
            val thisMessage = FirestoreResponse(
                subject = subject,
                fsid = fsid,
                description
                = description,
                author = author,
                authorid=authorid
            )
            Log.d("FirestoreADD",thisMessage.toString())
            val addition = responsesRef.document(fsid).set(thisMessage).await()
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

        override fun addResponseToFirestore(response: FirestoreResponse): Flow<Response<Void?>> = flow {
            try {
                emit(Response.Loading)
                val id = response.fsid

                val addition = id?.let { responsesRef.document(it).set(response).await() }
                emit(Response.Success(addition))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }


        override fun deleteResponseFromFirestore(fsid: String): Flow<Response<Void?>> = flow {
            try {
                emit(Response.Loading)
                val deletion = responsesRef.document(fsid).delete().await()
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