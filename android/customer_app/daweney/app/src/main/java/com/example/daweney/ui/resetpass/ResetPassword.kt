package com.example.daweney.ui.resetpass

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.daweney.R
import com.example.daweney.pojo.resetpass.ResetPassword
import com.example.daweney.ui.login.Login
import com.example.daweney.ui.dialog.CustomDialogFragment
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPassword : AppCompatActivity(), TextWatcher {

    private lateinit var resetPassword: ResetPasswordViewModel
    private lateinit var email: String
    private lateinit var newPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        //watcher
        passwordEditText.addTextChangedListener(this)


        resetPassword = ViewModelProvider(this)[ResetPasswordViewModel::class.java]
        resetPassButton.setOnClickListener {
            newPassword = passwordEditText.text.toString()
            resetPassword.resetPassword(ResetPassword(email, newPassword))
        }
        getDataFormIntent()
        progressBarObserve()
        dialogMessage()
        resetPasswordSuccessful()

    }

    private fun resetPasswordSuccessful() {
        resetPassword.loginMutableLiveData.observe(this) {
            val intent = Intent(this, Login::class.java)
            intent.putExtra("email", email)
            intent.putExtra("pass", newPassword)
            startActivity(intent)
            finish()

        }
    }

    private fun dialogMessage() {
        resetPassword.dialogMessage.observe(this) {
            if(it!=""){
            val dialogFragment = CustomDialogFragment(it, applicationContext)
            dialogFragment.show(supportFragmentManager, "reset_password_error_dialog")
        }}
    }

    private fun progressBarObserve() {
        resetPassword.progressBar.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
                resetPassButton.isEnabled=false
            } else {
                progressBar.visibility = View.INVISIBLE
                resetPassButton.isEnabled=true
            }
        }
    }


    private fun getDataFormIntent() {//get extra data form intent

        email = intent.getStringExtra("email").toString()
        Log.d("resetpass", email)
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        resetPassButton.isEnabled = passwordEditText.text?.trim()?.isNotEmpty() == true
    }

    override fun afterTextChanged(s: Editable?) {

    }
}