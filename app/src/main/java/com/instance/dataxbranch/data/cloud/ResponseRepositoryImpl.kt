package com.instance.dataxbranch.data.cloud

import android.util.Log

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
        //override var responsesRef: CollectionReference
    ): ResponseRepository {


        override fun getResponseById(fsid: String):Flow<Response<CloudResponse>> = callbackFlow {
           /* val snapshotListener = responsesRef.document(fsid).addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val Rresponse = snapshot.toObject(CloudResponse::class.java)!!
                    Response.Success(Rresponse)
                } else {
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
            awaitClose {
                snapshotListener.remove()
            }*/
        }

        override fun getResponsesFromCloud(): Flow<Response<List<CloudResponse>>> = callbackFlow {
            /*val snapshotListener = responsesRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val responses = snapshot.toObjects(Cloud
                            Response::class.java)
                    Response.Success(responses)
                } else {
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
            awaitClose {
                snapshotListener.remove()
            }*/
        }

    override fun addResponseToCloud
                (
        subject: String,
        description: String,
        author: String,
        authorid: String
    ): Flow<Response<Void?>> =flow{
        try {
            emit(Response.Loading)
            //val fsid = responsesRef.document().id
            val id = "STUB"
            val thisMessage = CloudResponse(
                subject = subject,
                fsid = id,
                description
                = description,
                author = author,
                authorid=authorid
            )
            Log.d("Cloud" +
                    "ADD",thisMessage.toString())
            //val addition = responsesRef.document(fsid).set(thisMessage).await()
            //emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    /*override fun addResponseToCloud
    (
        uname: String, data
        : String, author: String
    ): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val id = responsesRef.document().id
            val response = Response()
            response.dateAdded= Date
            Log.d("Cloud
            ADD", response.toString())
            val addition = responsesRef.document(id).set(response).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }*/

        override fun addResponseToCloud(response: CloudResponse): Flow<Response<Void?>> = flow {
            try {
                emit(Response.Loading)

                //val id = if(response.fsid==""){responsesRef.document().id}else{response.fsid}

                //val addition = id.let { responsesRef.document(it).set(response).await() }
                //emit(Response.Success(addition))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }


        override fun deleteResponseFromCloud(fsid: String): Flow<Response<Void?>> = flow {
            try {
                emit(Response.Loading)
                //val deletion = responsesRef.document(fsid).delete().await()
                //emit(Response.Success(deletion))

            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }

        /*override fun addLocalResponseToCloud
        (response: com.instance.dataxbranch.data.entities.Response) = flow {
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