package com.instance.dataxbranch.data.cloud



import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.CloudQuest
import com.surrealdb.connection.SurrealWebSocketConnection
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow


interface CloudGeneralRepository{
   // var questsRef: CollectionReference
    fun getSignedInUser(): String
    fun getQuestById(qid: String):Flow<Response<Quest>>
    fun getQuestsFromCloud(): Flow<Response<List<Quest>>>

   //ORIGINALLY HAD SUSPEND FUN
   fun addQuestToCloud(title: String, description: String, author:String): Flow<Response<CloudQuest>>
    fun addQuestToCloud(quest: CloudQuest): Flow<Response<Void?>>
   fun addQuestToCloud(quest: Quest): Flow<Response<Void?>>
   fun deleteQuestFromCloud(qid: String): Flow<Response<Void?>>
//   fun connect()
 //  fun mintNFTQuest(cloudQuest: CloudQuest) :Boolean

    fun deleteQuestFromCloud(quest: CloudQuest): Flow<Response<Void?>>
    fun connect(): Flow<Response<SurrealWebSocketConnection>>
}
@Module
@InstallIn(ViewModelComponent::class)
abstract class CloudModule {

    @Binds
    abstract fun bindCloudGeneralRepository(
        cloudGeneralRepositoryImpl: CloudGeneralRepositoryImpl//        CloudServiceImpl
    ): CloudGeneralRepository
}



