package com.example.daweney.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daweney.pojo.dataclass.LoginResponse
import com.example.daweney.pojo.dataclass.LoginUser
import com.example.daweney.pojo.dataclass.RegisterResponse
import com.example.daweney.pojo.dataclass.RegisterUser
import com.example.daweney.repo.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel :ViewModel() {
private val _loginMutableLiveData=MutableLiveData<LoginResponse>()
private val _progressBar= MutableLiveData<Boolean>()
private val _dialogMessage= MutableLiveData("")
private val _emailIsVerified=MutableLiveData<Boolean>()
private val  userRepository= UserRepository()
   // livedata which use to observe change
val loginMutableLiveData:LiveData<LoginResponse>
    get() =_loginMutableLiveData
val progressBar:LiveData<Boolean>
    get()=_progressBar
val dialogMessage:LiveData<String>
    get() = _dialogMessage
val emailIsVerified:LiveData<Boolean>
    get() = _emailIsVerified

     fun login(user: LoginUser){
        _progressBar.postValue(true)
        userRepository.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                when (response.code()) {
                    200 -> {
                        _loginMutableLiveData.value = response.body()
                        Log.d("response", "onResponse:done ")
                    }
                    400 -> {
                        Log.d("response", "onResponse: 400")
                        _dialogMessage.postValue("user name or password are wrong")
                    }
                    401 -> {
                        Log.d("response", "onResponse: 401")
                        _emailIsVerified.postValue(false)

                   }

                    else -> {
                        Log.d("response", "onResponse: else ")
                        _dialogMessage.postValue("have a error try again ")
                    }

                }
                _progressBar.postValue(false)
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("response", "onResponse: failure")
                _dialogMessage.postValue("can't login try again ")
                _progressBar.postValue(false)
            }

        })
    }




}