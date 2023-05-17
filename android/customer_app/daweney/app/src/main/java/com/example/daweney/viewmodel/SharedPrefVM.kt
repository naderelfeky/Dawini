package com.example.daweney.viewmodel

import androidx.lifecycle.ViewModel
import com.example.daweney.repo.SharedPrefRepo

class SharedPrefVM(private val sharedPrefRepository: SharedPrefRepo) : ViewModel() {
    fun saveData(key: String, value: String) {
        sharedPrefRepository.saveData(key, value)
    }

    fun getData(key: String, defaultValue: String): String? {
        return sharedPrefRepository.getData(key, defaultValue)
    }
}