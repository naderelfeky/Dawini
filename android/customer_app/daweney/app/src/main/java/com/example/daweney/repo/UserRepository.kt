package com.example.daweney.repo


import com.example.daweney.data.UserClient
import com.example.daweney.pojo.forgotpass.EmailUser
import com.example.daweney.pojo.forgotpass.MsgResponse
import com.example.daweney.pojo.login.LoginResponse
import com.example.daweney.pojo.login.LoginUser
import com.example.daweney.pojo.register.RegisterResponse
import com.example.daweney.pojo.register.RegisterUser
import com.example.daweney.pojo.resetpass.ResetPassword
import com.example.daweney.pojo.verifyaccount.VerifyUser
import retrofit2.Call

class UserRepository {

        fun login(user: LoginUser):Call<LoginResponse>{
            return UserClient.create().login(user)
        }

        fun register(user: RegisterUser):Call<RegisterResponse>{
            return UserClient.create().register(user)
        }

        fun sendCode(email: EmailUser):Call<MsgResponse>{
            return UserClient.create().sendCode(email)
        }

        fun verifyUser(verifyUser: VerifyUser):Call<com.example.daweney.pojo.verifyaccount.MsgResponse>{
            return UserClient.create().verifyUser(verifyUser)
        }
        fun resetPassword(resetPassword: ResetPassword):Call<com.example.daweney.pojo.resetpass.MsgResponse>{
            return UserClient.create().resetPassword(resetPassword)
        }

}