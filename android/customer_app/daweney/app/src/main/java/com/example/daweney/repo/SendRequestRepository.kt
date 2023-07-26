package com.example.daweney.repo

import com.example.daweney.data.UserClient
import com.example.daweney.pojo.send_request.SendRequestBody
import com.example.daweney.pojo.send_request.SendRequestResponse
import retrofit2.Call

class SendRequestRepository {
fun sendRequest(sendRequestBody: SendRequestBody):Call<SendRequestResponse>{
    return UserClient.create().sendRequest(sendRequestBody)
}

}