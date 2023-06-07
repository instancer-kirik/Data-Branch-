package com.instance.dataxbranch.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object constants {
    const val FIRESTORE_COLLECTION = "quests"
    const val NAME_PROPERTY = "title"
    const val DEFAULT_UNAME = "DEFAULT UNAME, PRESS REFRESH TO LOAD"
    val _tabCurrentStatus = MutableLiveData(0)
    val tabCurrentStatus: LiveData<Int> = _tabCurrentStatus
    const val GITHUB_CLIENT_SECRET="MBobYJnIUkF4V+Pqfg4IP4pSzhZD/itF4G0OZzk8ncQ="//oNPGes+/DOuTZ4J8US4qeKLi3uh+thiGhTsPl3LeAyY="
    const val GITHUB_CLIENT_ID= "Iv1.034d85f108cef075"

}