package com.example.daweney.pojo

import com.example.daweney.pojo.dataclass.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    //   https://daweney.onrender.com/customers/signin

      @POST("signin")
      fun login(@Body user: LoginUser):Call<LoginResponse>

      @POST("signup")
      fun register(@Body user: RegisterUser):Call<RegisterResponse>

      @POST("verifyusr")
      fun verifyUser(@Body verifyUser: VerifyUser):Call<MsgResponse>

      @POST("sendcode")
      fun sendCode(@Body emailUser: EmailUser):Call<MsgResponse>

      @POST("resetpass")
      fun resetPassword(@Body resetPassword: ResetPassword):Call<MsgResponse>
//    @POST("signin")
//    fun register(@Body account:RegisterAccount):Call<HashMap<Any,Any>>

//
//    @POST("signin")  //customer signin
//     fun storePost(@Body map:HashMap<Any, Any>):Call<LoginReturn>
//
//     @POST("signup")
//     fun signUp()
}