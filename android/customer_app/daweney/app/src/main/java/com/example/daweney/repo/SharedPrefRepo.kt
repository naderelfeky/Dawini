package com.example.daweney.repo

import android.content.Context

class SharedPrefRepo(context:Context) {
    companion object{
        const val FILE_NAME="myPrefs"
        const val EMAIL="EMAIL"
        const val PASS="PASSWORD"
    }
    private val sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getData(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }
}