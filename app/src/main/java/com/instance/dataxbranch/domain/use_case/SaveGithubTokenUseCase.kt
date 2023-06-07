package com.instance.dataxbranch.domain.use_case

import android.util.Log
import com.instance.dataxbranch.github.GithubRepository
import javax.inject.Inject

class SaveGithubTokenUseCase @Inject constructor(private val repository: GithubRepository) {
    operator fun invoke(token: String): Unit  {repository.addGithubToken(token)
        Log.d("SaveGithubTokenUseCase", "invoke: $token")}
}