package com.instance.dataxbranch.data.firestore


import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.instance.dataxbranch.domain.Response
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalCoroutinesApi::class)
interface   SocialRepository {

        var chatRoomRef: CollectionReference
        fun getChatRoomById(fsid: String): Flow<Response<FirestoreChatRoom>>
        fun getChatRoomsFromFirestore(): Flow<Response<List<FirestoreChatRoom>>>

        //ORIGINALLY HAD SUSPEND FUN
        //fun addMessageToFirestoreChatRoom( subject: String, description: String, author: String, authorid: String ): Flow<Response<Void?>>
        fun addChatRoomToFirestore(subject: String, description: String, author:String,authorid:String): Flow<Response<Void?>>
        fun addChatRoomToFirestore(room: FirestoreChatRoom): Flow<Response<Void?>>
        fun deleteChatRoomFromFirestore(fsid: String): Flow<Response<Void?>>
    }
    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class SocialModule {

        @Binds
        abstract fun bindSocialRepository(
            SocialServiceImpl: SocialRepositoryImpl
        ): SocialRepository
    }
