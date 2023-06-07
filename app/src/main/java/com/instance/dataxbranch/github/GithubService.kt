package com.instance.dataxbranch.github

import android.util.Log
import com.instance.dataxbranch.data.local.Preferences
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io. ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.ContentConverter
import io.ktor.serialization.kotlinx.json.json


import kotlinx.serialization.json.Json as serializer
import javax.inject.Inject

class GithubService @Inject constructor(private val preferences: Preferences){

    private val client = HttpClient(CIO) {

        val token = preferences.getGithubToken() //?: throw IllegalStateException("Github token not found")
        Log.d("GithubService", "GithubService: $token")
        defaultRequest {
            header("Authorization", "token $token")
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {

            json()
        }
}
    suspend fun getProfile(): GithubUserDto {
        val response = client.get("https://api.github.com/user")
        return response.body()
    }
}