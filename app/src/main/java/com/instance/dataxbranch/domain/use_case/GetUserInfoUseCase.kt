package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.domain.GithubUser
import com.instance.dataxbranch.github.GithubRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val githubRepository: GithubRepository) {
    suspend operator fun invoke(): GithubUser {
        return githubRepository.getUserInfo()
    }
}