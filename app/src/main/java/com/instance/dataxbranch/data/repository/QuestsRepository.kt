package com.instance.dataxbranch.data.repository

import com.google.firebase.firestore.CollectionReference
import com.instance.dataxbranch.Quest
import com.instance.dataxbranch.domain.Response
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


interface QuestsRepository{
    var questsRef: CollectionReference
    fun getQuestById(qid: String){TODO()}
    fun getQuestsFromFirestore(): Flow<Response<List<Quest>>>

   //ORIGINALLY HAD SUSPEND FUN
   suspend fun addQuestToFirestore(title: String, description: String): Flow<Response<Void?>>
    suspend fun deleteQuestFromFirestore(qid: String): Flow<Response<Void?>>
}
@Module
@InstallIn(ViewModelComponent::class)
abstract class QuestsModule {

    @Binds
    abstract fun bindQuestRepository(
        QuestServiceImpl: QuestRepositoryImpl
    ): QuestsRepository
}



