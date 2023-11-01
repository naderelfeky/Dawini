package com.example.daweney.ui.sharedPref

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daweney.repo.SharedPrefRepo

class SharedPreferenceRepoFactory(private val myArg: SharedPrefRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedPrefVM::class.java)) {
            return SharedPrefVM(myArg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}