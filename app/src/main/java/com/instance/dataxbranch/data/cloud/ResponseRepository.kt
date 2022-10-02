package com.instance.dataxbranch.data.cloud


import com.instance.dataxbranch.domain.Response

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface  ResponseRepository {
    //var responsesRef: CollectionReference
    fun getResponseById(cloud_id: String): Flow<Response<CloudResponse>>
    fun getResponsesFromCloud(): Flow<Response<List<CloudResponse>>>

    //ORIGINALLY HAD SUSPEND FUN
    fun addResponseToCloud(subject: String, description: String, author:String,authorid:String): Flow<Response<Void?>>
    fun addResponseToCloud(response: CloudResponse): Flow<Response<Void?>>
    fun deleteResponseFromCloud(fsid: String): Flow<Response<Void?>>
}
    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class ResponseModule {

        @Binds
        abstract fun bindResponseRepository(
            ResponseServiceImpl: ResponseRepositoryImpl
        ): ResponseRepository
    }