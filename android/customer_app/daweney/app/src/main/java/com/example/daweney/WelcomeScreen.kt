package com.example.daweney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.welcome.*

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)
       btn_welcome.setOnClickListener {
           startActivity(Intent("android.intent.action.login"))
           finish()
       }
    }

}