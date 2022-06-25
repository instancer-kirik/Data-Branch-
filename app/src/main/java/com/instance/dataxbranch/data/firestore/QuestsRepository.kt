package com.instance.dataxbranch.data.firestore

import com.google.firebase.firestore.CollectionReference
import com.instance.dataxbranch.data.firestore.QuestRepositoryImpl
import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.domain.Response
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow


interface QuestsRepository{
    var questsRef: CollectionReference
    fun getQuestById(qid: String){TODO()}
    fun getQuestsFromFirestore(): Flow<Response<List<Quest>>>

   //ORIGINALLY HAD SUSPEND FUN
   fun addQuestToFirestore(title: String, description: String, author:String): Flow<Response<Void?>>
   fun addQuestToFirestore(quest: Quest): Flow<Response<Void?>>
   fun deleteQuestFromFirestore(qid: String): Flow<Response<Void?>>
}
@Module
@InstallIn(ViewModelComponent::class)
abstract class QuestsModule {

    @Binds
    abstract fun bindQuestRepository(
        QuestServiceImpl: QuestRepositoryImpl
    ): QuestsRepository
}



