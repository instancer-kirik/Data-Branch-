package com.instance.dataxbranch.github

import com.instance.dataxbranch.domain.GithubUser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface GithubRepository {
    fun addGithubToken(token: String)

    suspend fun getUserInfo(): GithubUser

}
/*
@Module
@InstallIn(ViewModelComponent::class)
abstract class GithubModule {

    @Binds
    abstract fun bindGithubRepository(
        githubRepositoryImpl: GithubRepositoryImpl
    ): GithubRepository
}*/
