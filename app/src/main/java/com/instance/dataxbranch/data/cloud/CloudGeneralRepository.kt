package com.instance.dataxbranch.data.cloud



import com.instance.dataxbranch.quests.Quest
import com.instance.dataxbranch.domain.Response
import com.instance.dataxbranch.quests.CloudQuest
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow


//interface CloudGeneralRepository{
//   // var questsRef: CollectionReference
//    fun getQuestById(qid: String):Flow<Response<Quest>>
//    fun getQuestsFromCloud(): Flow<Response<List<Quest>>>
//
//   //ORIGINALLY HAD SUSPEND FUN
//   fun addQuestToCloud(title: String, description: String, author:String): Flow<Response<Void?>>
//   fun addQuestToCloud(quest: Quest): Flow<Response<Void?>>
//   fun deleteQuestFromCloud(qid: String): Flow<Response<Void?>>
//
// //  fun mintNFTQuest(cloudQuest: CloudQuest) :Boolean
//
//
//}
//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class CloudModule {
//
//    @Binds
//    abstract fun bindCloudGeneralRepository(
////        CloudServiceImpl
//        CloudGeneralRepositoryImpl: CloudGeneralRepositoryImpl
//    ): CloudGeneralRepository
//}
//


