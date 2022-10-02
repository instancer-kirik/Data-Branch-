package com.instance.dataxbranch

import android.app.Application
//import com.getstream.sdk.chat.utils.DateFormatter
//import com.google.firebase.FirebaseApp
//import com.instance.dataxbranch.data.PredefinedUserCredentials
//import com.instance.dataxbranch.data.UserCredentialsRepository
//import com.instance.dataxbranch.social.StreamChat.ChatHelper
import dagger.hilt.android.HiltAndroidApp
//import io.getstream.chat.android.client.utils.internal.toggle.ToggleService
//import io.getstream.chat.android.core.internal.InternalStreamChatApi

@HiltAndroidApp
class DataBranchApp : Application(){
    override fun onCreate() {
        super.onCreate()
        //FirebaseApp.initializeApp(this)
//        credentialsRepository = UserCredentialsRepository(this)
        //dateFormatter = DateFormatter.from(this)

//        initializeToggleService()
//        ChatHelper.initializeSdk(this, getApiKey())

    }

//    private fun getApiKey(): String {
//        return credentialsRepository.loadApiKey() ?: PredefinedUserCredentials.API_KEY
//    }

//    @OptIn(InternalStreamChatApi::class)
//    private fun initializeToggleService() {
//        ToggleService.init(applicationContext, mapOf(ToggleService.TOGGLE_KEY_SOCKET_REFACTOR to BuildConfig.DEBUG))
//    }

    companion object {
//        lateinit var credentialsRepository: UserCredentialsRepository
//            private set

//        lateinit var dateFormatter: DateFormatter
//            private set
    }
    //val navController = rememberNavHostEngine()
    //val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
