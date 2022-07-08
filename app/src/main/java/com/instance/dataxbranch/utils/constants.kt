package com.instance.dataxbranch.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object constants {
    const val FIRESTORE_COLLECTION = "quests"
    const val NAME_PROPERTY = "title"
    const val DEFAULT_UNAME = "DEFAULT UNAME, PRESS REFRESH TO LOAD"
    val _tabCurrentStatus = MutableLiveData(0)
    val tabCurrentStatus: LiveData<Int> = _tabCurrentStatus

}