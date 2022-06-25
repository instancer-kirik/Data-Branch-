package com.instance.dataxbranch.data.firestore

//import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.CollectionReference

import com.instance.dataxbranch.core.Constants.TITLE
import com.instance.dataxbranch.data.entities.User


import com.instance.dataxbranch.domain.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepositoryImpl @Inject constructor(
    override var usersRef: CollectionReference
): UsersRepository {


    override fun getUserById(fsid: String) {
        TODO("Not yet implemented")
    }

    override fun getUsersFromFirestore(): Flow<Response<List<User>>> = callbackFlow {
        val snapshotListener = usersRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
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
        }
    }

    /*override fun addUserToFirestore(
        uname: String, data
        : String, author: String
    ): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val id = usersRef.document().id
            val user = User()
            user.dateAdded= Date
            Log.d("FirestoreADD", user.toString())
            val addition = usersRef.document(id).set(user).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }*/


    override fun addUserToFirestore(user: User): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val id = user.fsid

            val addition = id?.let { usersRef.document(it).set(user).await() }
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }


    override fun deleteUserFromFirestore(fsid: String): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val deletion = usersRef.document(fsid).delete().await()
            emit(Response.Success(deletion))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun addLocalUserToFirestore(user: com.instance.dataxbranch.data.entities.User) = flow {
        try {
            emit(Response.Loading)
            val id = user.fsid

            val addition = id?.let { usersRef.document(it).set(user).await() }
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }
}