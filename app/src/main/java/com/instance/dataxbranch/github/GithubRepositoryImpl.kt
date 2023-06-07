package com.instance.dataxbranch.github

import com.instance.dataxbranch.data.local.Preferences
import com.instance.dataxbranch.domain.GithubUser

import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val preferences: Preferences,
    private val githubService: GithubService
): GithubRepository {
    override fun addGithubToken(token: String) {
        preferences.setGithubToken(token)
    }

    override suspend fun getUserInfo(): GithubUser {
        return githubService.getProfile().toDomain()
    }
}