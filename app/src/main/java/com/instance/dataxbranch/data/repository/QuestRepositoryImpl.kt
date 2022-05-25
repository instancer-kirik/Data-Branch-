package com.instance.dataxbranch.data.repository

import com.google.firebase.firestore.CollectionReference
import com.instance.dataxbranch.Quest
import com.instance.dataxbranch.core.Constants.TITLE
import com.instance.dataxbranch.di.AppModule.provideFirebaseFirestore
import com.instance.dataxbranch.di.AppModule.provideQuestCollRef

import com.instance.dataxbranch.domain.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class QuestRepositoryImpl @Inject constructor(
    override var questsRef: CollectionReference
): QuestsRepository {



    override fun getQuestById(qid: String) {
        TODO("Not yet implemented")
    }

    override fun getQuestsFromFirestore(): Flow<Response<List<Quest>>> = callbackFlow {
        val snapshotListener = questsRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val quests = snapshot.toObjects(Quest::class.java)
                Response.Success(quests)
            } else {
                Response.Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addQuestToFirestore(title: String, description
    : String): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val id = questsRef.document().id.toString()
            val quest = Quest(
                qid = id,
                title = title,
                description
                = description

            )
            val addition = questsRef.document(id).set(quest).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }



    override suspend fun deleteQuestFromFirestore(qid: String): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val deletion = questsRef.document(qid).delete().await()
            emit(Response.Success(deletion))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }
}