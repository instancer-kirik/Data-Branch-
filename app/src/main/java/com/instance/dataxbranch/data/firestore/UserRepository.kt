package com.instance.dataxbranch.data.firestore

import com.google.firebase.firestore.CollectionReference
import com.instance.dataxbranch.data.entities.User
import com.instance.dataxbranch.data.firestore.UserRepositoryImpl

//import com.google.firebase.firestore.auth.User
import com.instance.dataxbranch.domain.Response

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface UsersRepository{
    var usersRef: CollectionReference
    fun getUserById(fsid: String):Flow<Response<User>>
    fun getFirestoreUserById(fsid: String):Flow<Response<FirestoreUser>>
    fun getUsersFromFirestore(): Flow<Response<List<User>>>

    //ORIGINALLY HAD SUSPEND FUN
   // fun addUserToFirestore(title: String, description: String, author:String): Flow<Response<Void?>>
    fun addUserToFirestore(user: User): Flow<Response<Void?>>
    fun addLocalUserToFirestore(user: com.instance.dataxbranch.data.entities.User): Flow<Response<Void?>>
    fun deleteUserFromFirestore(fsid: String): Flow<Response<Void?>>
}
@Module
@InstallIn(ViewModelComponent::class)
abstract class UsersModule {

    @Binds
    abstract fun bindUserRepository(
        UserServiceImpl: UserRepositoryImpl
    ): UsersRepository
}