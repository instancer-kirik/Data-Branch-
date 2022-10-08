package com.instance.dataxbranch.data.cloud

//import com.google.firebase.Cloud.auth.User
//import android.util.Log

import com.instance.dataxbranch.core.Constants.TAG

import com.instance.dataxbranch.core.Constants.TITLE
import com.instance.dataxbranch.data.entities.User


import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.Quest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepositoryImpl @Inject constructor(
    //override var usersRef: CollectionReference
): UsersRepository {


    override fun getCloudUserById(fsid: String) : Flow<Response<CloudUser>> = callbackFlow {
      /*  val snapshotListener =usersRef.document(fsid).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val user = snapshot.toObject(CloudUser::class.java)
                Response.Success(user)
            } else {
                Response.Error(e?.message ?: e.toString())
            }
            trySend(response as Response<CloudUser>).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
*/

    }
    override fun getUserById(fsid: String) : Flow<Response<User>> = callbackFlow {
      /*  val snapshotListener =usersRef.document(fsid).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val user = snapshot.toObject(User::class.java)
                Response.Success(user)
            } else {
                Response.Error(e?.message ?: e.toString())
            }
            trySend(response as Response<User>).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
*/

    }
    override fun getUsersFromCloud(): Flow<Response<List<User>>> = callbackFlow {
       /* val snapshotListener = usersRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val users = snapshot.toObjects(User::class.java)
                Response.Success(users)
            } else {
                Response.Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }*/
    }

    /*override fun addUserToCloud(
        uname: String, data
        : String, author: String
    ): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val id = usersRef.document().id
            val user = User()
            user.dateAdded= Date
            Log.d("CloudADD", user.toString())
            val addition = usersRef.document(id).set(user).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }*/

    override fun addUserToCloud(user: User): Flow<Response<Void?>> = flow {
        try {
//TODO(STUB)
            //Log.d(TAG,"STUB in userrepimpl")
           /* emit(Response.Loading)
            val id = user.fsid

            val addition = id?.let { usersRef.document(it).set(user).await() }
            emit(Response.Success(addition))*/
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }


    override fun deleteUserFromCloud(fsid: String): Flow<Response<Void?>> = flow {
     /*   try {
            emit(Response.Loading)
            val deletion = usersRef.document(fsid).delete().await()
            emit(Response.Success(deletion))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }*/
    }

    override fun addLocalUserToCloud(user: com.instance.dataxbranch.data.entities.User) = flow {
        try {
//TODO(STUB)
           // Log.d(TAG,"STUB in userrepimpl")
            /*emit(Response.Loading)
            val id = user.fsid

            val addition = id?.let { usersRef.document(it).set(user).await() }
            emit(Response.Success(addition))*/
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }
}