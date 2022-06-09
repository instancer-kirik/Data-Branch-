package com.instance.dataxbranch

import android.app.Application
import com.google.firebase.FirebaseApp
import com.instance.dataxbranch.data.local.AppDatabase
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DataBranchApp : Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
    //val navController = rememberNavHostEngine()
    //val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
