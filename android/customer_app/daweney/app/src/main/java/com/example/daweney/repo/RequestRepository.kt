package com.example.daweney.repo

import com.example.daweney.data.UserClient
import com.example.daweney.pojo.myreqests.RequestBody
import com.example.daweney.pojo.myreqests.RequestResponse
import retrofit2.Call

class RequestRepository {

    fun getMyRequests(requestBody: RequestBody): Call<RequestResponse> {
        return UserClient.create().getMyRequests(requestBody)
    }
}