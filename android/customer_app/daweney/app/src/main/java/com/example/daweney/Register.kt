package com.example.daweney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.ET_pass
import kotlinx.android.synthetic.main.login.ET_phone
import kotlinx.android.synthetic.main.register.*

class Register : AppCompatActivity() , TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        // Get input text
        //btn_login.isEnabled=true
        ET_phone.addTextChangedListener(this)
        ET_pass.addTextChangedListener(this)
        ET_confirm_pass.addTextChangedListener(this)
        btn_sign_up.setOnClickListener{
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()

        }
        TV_login.setOnClickListener {
            startActivity(Intent(applicationContext,Login::class.java))
            finish()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        btn_sign_up.isEnabled = (ET_phone.text.toString().trim().isNotEmpty() && ET_pass.text.toString().trim().isNotEmpty()&& ET_confirm_pass.text.toString().trim().isNotEmpty())

    }

    override fun afterTextChanged(s: Editable?) {

    }
}