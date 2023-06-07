package com.instance.dataxbranch.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preferences @Inject constructor(@ApplicationContext context: Context) {
    private val githubTokenKey = "GITHUB_TOKEN"
    val PREF_FILE_NAME = "INSTANCE_PREFS"
    private val preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    fun getGithubToken() = preferences.getString(githubTokenKey, null)

    fun setGithubToken(token: String?) = preferences.edit().putString(githubTokenKey, token).apply()

    fun clearprefs() {

        val e: SharedPreferences.Editor = preferences.edit()
        e.clear()
        e.commit()
    //preferences.edit().clear().apply()
}

}