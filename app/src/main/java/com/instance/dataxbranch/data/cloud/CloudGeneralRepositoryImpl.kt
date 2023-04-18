package com.instance.dataxbranch.data.cloud

import androidx.compose.runtime.rememberCoroutineScope
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.Quest
import kotlinx.coroutines.flow.Flow
class CloudGeneralRepositoryImpl : CloudGeneralRepository {
    override fun getQuestById(qid: String): Flow<Response<Quest>> {
        TODO()
    }

    override fun getQuestsFromCloud(): Flow<Response<List<Quest>>> {
        TODO()
    }

    override fun addQuestToCloud(title: String, description: String, author: String): Flow<Response<Void?>> {
        TODO()
    }

    override fun addQuestToCloud(quest: Quest): Flow<Response<Void?>> {
        TODO()
    }

    override fun deleteQuestFromCloud(qid: String): Flow<Response<Void?>> {
        TODO()
    }
}
/*
    // var questsRef: CollectionReference
    fun getQuestById(qid: String): Flow<Response<Quest>> {TODO()}
    fun getQuestsFromCloud(): Flow<Response<List<Quest>>> {TODO()}

    //ORIGINALLY HAD SUSPEND FUN
    fun addQuestToCloud(title: String, description: String, author:String): Flow<Response<Void?>> {TODO()}
    fun addQuestToCloud(quest: Quest): Flow<Response<Void?>> {TODO()}
    fun deleteQuestFromCloud(qid: String): Flow<Response<Void?>> {TODO()}

    //  fun mintNFTQuest(cloudQuest: CloudQuest) :Boolean



}
*/

