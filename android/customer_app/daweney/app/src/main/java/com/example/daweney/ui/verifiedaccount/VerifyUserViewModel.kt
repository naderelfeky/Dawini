package com.example.daweney.ui.verifiedaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daweney.pojo.verifyaccount.MsgResponse
import com.example.daweney.pojo.verifyaccount.VerifyUser
import com.example.daweney.repo.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyUserViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val _verifyUserMutableLiveData = MutableLiveData<MsgResponse>()
    private val _progressBar = MutableLiveData<Boolean>()
    private val _dialogMessage = MutableLiveData("")
    val progressBar: LiveData<Boolean>
        get() = _progressBar
    val verifyUserMutableLiveData: MutableLiveData<MsgResponse>
        get() = _verifyUserMutableLiveData
    val dialogMessage: LiveData<String>
        get() = _dialogMessage

    fun verifyUser(verifyUser: VerifyUser) {
        _progressBar.postValue(true)
        userRepository.verifyUser(verifyUser).enqueue(object : Callback<MsgResponse> {
            override fun onResponse(call: Call<MsgResponse>, response: Response<MsgResponse>) {
                when (response.code()) {
                    200 -> {
                        _verifyUserMutableLiveData.postValue(response.body())
                    }
                    400 -> {
                        _dialogMessage.postValue("code is wrong !")
                    }
                    else -> {
                        _dialogMessage.postValue("error try again")
                    }
                }
                _progressBar.postValue(false)
            }

            override fun onFailure(call: Call<MsgResponse>, t: Throwable) {
                _dialogMessage.postValue("failure try again")
                _progressBar.postValue(false)
            }
        })

    }
}