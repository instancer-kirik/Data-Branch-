package com.instance.dataxbranch.data.firestore

import com.google.firebase.firestore.CollectionReference
import com.instance.dataxbranch.domain.Response

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface  ResponseRepository {
    var responsesRef: CollectionReference
    fun getResponseById(fsid: String): Flow<Response<FirestoreResponse>>
    fun getResponsesFromFirestore(): Flow<Response<List<FirestoreResponse>>>

    //ORIGINALLY HAD SUSPEND FUN
    fun addResponseToFirestore(subject: String, description: String, author:String,authorid:String): Flow<Response<Void?>>
    fun addResponseToFirestore(response: FirestoreResponse): Flow<Response<Void?>>
    fun deleteResponseFromFirestore(fsid: String): Flow<Response<Void?>>
}
    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class ResponseModule {

        @Binds
        abstract fun bindResponseRepository(
            ResponseServiceImpl: ResponseRepositoryImpl
        ): ResponseRepository
    }