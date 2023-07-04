package com.example.daweney.data

import com.example.daweney.pojo.*
import com.example.daweney.pojo.forgotpass.EmailUser
import com.example.daweney.pojo.forgotpass.MsgResponse
import com.example.daweney.pojo.login.LoginResponse
import com.example.daweney.pojo.login.LoginUser
import com.example.daweney.pojo.myreqests.RequestBody
import com.example.daweney.pojo.myreqests.RequestResponse
import com.example.daweney.pojo.register.RegisterResponse
import com.example.daweney.pojo.register.RegisterUser
import com.example.daweney.pojo.resetpass.ResetPassword
import com.example.daweney.pojo.services.ServicesBody
import com.example.daweney.pojo.services.ServicesResponse
import com.example.daweney.pojo.services_category.ServicesCategoryResponse
import com.example.daweney.pojo.verifyaccount.VerifyUser
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    //   https://daweney.onrender.com/customers/signin

      @POST("signin")
      fun login(@Body user: LoginUser):Call<LoginResponse>

      @POST("signup")
      fun register(@Body user: RegisterUser):Call<RegisterResponse>

      @POST("verifyusr")
      fun verifyUser(@Body verifyUser: VerifyUser):Call<com.example.daweney.pojo.verifyaccount.MsgResponse>

      @POST("sendcode")
      fun sendCode(@Body emailUser: EmailUser):Call<MsgResponse>

      @POST("resetpass")
      fun resetPassword(@Body resetPassword: ResetPassword):Call<com.example.daweney.pojo.resetpass.MsgResponse>

      @POST("getreq")
       fun getMyRequests(@Body requestBody:RequestBody):Call<RequestResponse>

       @GET("get-srvc")
       fun getServicesCategory():Call<ServicesCategoryResponse>

       @POST("findservice")
       fun getServices(@Body servicesBody: ServicesBody):Call<ServicesResponse>

       @POST("")
       fun getApplication()
//    @POST("signin")
//    fun register(@Body account:RegisterAccount):Call<HashMap<Any,Any>>

//
//    @POST("signin")  //customer signin
//     fun storePost(@Body map:HashMap<Any, Any>):Call<LoginReturn>
//
//     @POST("signup")
//     fun signUp()
}