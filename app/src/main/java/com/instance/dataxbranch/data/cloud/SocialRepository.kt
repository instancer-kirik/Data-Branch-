package com.instance.dataxbranch.data.cloud



import com.instance.dataxbranch.data.cloud.SocialRepositoryImpl
import com.instance.dataxbranch.data.firestore.CloudChatRoom
import com.instance.dataxbranch.domain.Response
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalCoroutinesApi::class)
interface   SocialRepository {

        //var chatRoomRef: CollectionReference

        fun getChatRoomById(fsid: String): Flow<Response<CloudChatRoom>>
        fun getChatRoomsFromCloud(): Flow<Response<List<CloudChatRoom>>>

        //ORIGINALLY HAD SUSPEND FUN
        //fun addMessageToCloudChatRoom( subject: String, description: String, author: String, authorid: String ): Flow<Response<Void?>>

        fun addChatRoomToCloud(title: String,
                                   subject: String,

                                   members: List<String>,
                                   /*recentMessageText: String,
                                   recentMessageSendBy: String,*/
        )//: Flow<Response<Void?>>

        fun addChatRoomToCloud(room: CloudChatRoom)//: Flow<Response<Void?>>
        fun deleteChatRoomFromCloud(fsid: String): Flow<Response<Void?>>
    fun addMessageToCloudChatRoom(
        cloudChatRoom: CloudChatRoom,
        text: String,

        name: String,
        imgUrl: String
    )//: Flow<Response<Void?>>
}
    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class SocialModule {

        @Binds
        abstract fun bindSocialRepository(
            SocialServiceImpl: SocialRepositoryImpl
        ): SocialRepository
    }
