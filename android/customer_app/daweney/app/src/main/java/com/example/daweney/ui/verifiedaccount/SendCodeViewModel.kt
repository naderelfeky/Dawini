package com.example.daweney.ui.verifiedaccount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daweney.pojo.forgotpass.EmailUser
import com.example.daweney.pojo.forgotpass.MsgResponse
import com.example.daweney.repo.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendCodeViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _codeMutableLiveData = MutableLiveData<MsgResponse>()
    private val _progressBar = MutableLiveData<Boolean>()
    private val _emailNotFound = MutableLiveData<Boolean>()
    private val _dialogMessage = MutableLiveData("")
    val progressBar: LiveData<Boolean>
        get() = _progressBar
    val emailNotFound: LiveData<Boolean>
        get() = _emailNotFound
    val verifyUserMutableLiveData: MutableLiveData<MsgResponse>
        get() = _codeMutableLiveData
    val dialogMessage: LiveData<String>
        get() = _dialogMessage

    fun sendCode(email: String) {
        _progressBar.postValue(true)
        userRepository.sendCode(EmailUser(email)).enqueue(object : Callback<MsgResponse> {
            override fun onResponse(call: Call<MsgResponse>, response: Response<MsgResponse>) {

                when (response.code()) {
                    200 -> {
                        _codeMutableLiveData.postValue(response.body())
                    }
                    400 -> {
                        _dialogMessage.postValue("you login with google , try login with it")
                    }
                    404 -> {
                        _dialogMessage.postValue("mail not found you should register first ")
                        _emailNotFound.postValue(true)
                    }
                    else -> {
                        _dialogMessage.postValue("error , try again ")
                    }

                }
                _progressBar.postValue(false)
            }

            override fun onFailure(call: Call<MsgResponse>, t: Throwable) {
                _dialogMessage.postValue("opps ,try again")
                _progressBar.postValue(false)
                Log.d("response", t.message.toString())
            }
        })
    }


}