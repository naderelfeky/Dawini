package com.example.daweney.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daweney.pojo.dataclass.RegisterResponse
import com.example.daweney.pojo.dataclass.RegisterUser
import com.example.daweney.repo.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel :ViewModel() {

    private val _registerMutableLiveData= MutableLiveData<RegisterResponse>()
    private val _progressBar= MutableLiveData<Boolean>()
    private val _dialogMessage= MutableLiveData("")
    private val  userRepository= UserRepository()
    // live data which use to
    val registerMutableLiveData: LiveData<RegisterResponse>
        get() =_registerMutableLiveData
    val progressBar: LiveData<Boolean>
        get()=_progressBar
    val dialogMessage: LiveData<String>
        get() = _dialogMessage




    fun register(user: RegisterUser){
        _progressBar.postValue(true)
        userRepository.register(user).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                when (response.code()) {
                    201 -> {
                        _registerMutableLiveData.value = response.body()
                        Log.d("response", "onResponse:done ")
                    }
                    400 -> {
                        Log.d("response", "onResponse: 400")
                        _dialogMessage.postValue("email are wrong")
                    }
                    409 -> {
                        Log.d("response", "onResponse: 409")
                        _dialogMessage.postValue("user already exist")
                    }

                    else -> {
                        Log.d("response", "onResponse: else ")
                        _dialogMessage.postValue("have a error try again ")
                    }

                }
                _progressBar.postValue(false)
            }


            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("response", "onResponse: failure")
                _dialogMessage.postValue("oops , can't register try again ")
                _progressBar.postValue(false)

            }
        })
    }
}