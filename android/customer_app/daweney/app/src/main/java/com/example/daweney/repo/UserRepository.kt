package com.example.daweney.repo


import com.example.daweney.pojo.UserClient
import com.example.daweney.pojo.dataclass.*
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

        fun verifyUser(verifyUser: VerifyUser):Call<MsgResponse>{
            return UserClient.create().verifyUser(verifyUser)
        }
        fun resetPassword(resetPassword: ResetPassword):Call<MsgResponse>{
            return UserClient.create().resetPassword(resetPassword)
        }

}