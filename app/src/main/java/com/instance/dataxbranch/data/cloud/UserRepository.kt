package com.instance.dataxbranch.data.cloud


import com.instance.dataxbranch.data.entities.User


//import com.google.firebase.Cloud.auth.User
import com.instance.dataxbranch.domain.Response

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface UsersRepository{
    //var usersRef: CollectionReference
    fun getUserById(fsid: String):Flow<Response<User>>
    fun getCloudUserById(fsid: String):Flow<Response<CloudUser>>
    fun getUsersFromCloud(): Flow<Response<List<User>>>

    //ORIGINALLY HAD SUSPEND FUN
   // fun addUserToCloud(title: String, description: String, author:String): Flow<Response<Void?>>
    fun addUserToCloud(user: User): Flow<Response<Void?>>
    fun addLocalUserToCloud(user: com.instance.dataxbranch.data.entities.User): Flow<Response<Void?>>
    fun deleteUserFromCloud(fsid: String): Flow<Response<Void?>>
}
@Module
@InstallIn(ViewModelComponent::class)
abstract class UsersModule {

    @Binds
    abstract fun bindUserRepository(
        UserServiceImpl: UserRepositoryImpl
    ): UsersRepository
}