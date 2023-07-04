package com.example.daweney.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.daweney.R
import com.example.daweney.repo.SharedPrefRepo
import com.example.daweney.ui.login.Login
import com.example.daweney.ui.myrequests.MyRequests
import com.example.daweney.ui.sharedPref.SharedPrefVM
import com.example.daweney.ui.sharedPref.SharedPreferenceRepoFactory
import kotlinx.android.synthetic.main.activity_splash_screen.*


@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var sharedPrefVM: SharedPrefVM
    private val SPLASH_DISPLAY_LENGTH = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //to set animation
        logo_animation.setAnimation(R.raw.daweney_logo)

        hideStatusBar()

        Handler().postDelayed({
           userSaveLogin()
        },SPLASH_DISPLAY_LENGTH.toLong())

    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun userSaveLogin() {
        val myArg = SharedPrefRepo(this)
        val factory = SharedPreferenceRepoFactory(myArg)

        sharedPrefVM = ViewModelProvider(this, factory)[SharedPrefVM::class.java]

        if (sharedPrefVM.getData(SharedPrefRepo.CUSTOMER_ID, "").toString().isNotEmpty()) {
            startMainActivity()
        }else{
            startLoginActivity()
        }
    }
    private fun startMainActivity() {
        val intent = Intent(this, MyRequests::class.java)
        startActivity(intent)
        finish()
    }
    private fun startLoginActivity() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}