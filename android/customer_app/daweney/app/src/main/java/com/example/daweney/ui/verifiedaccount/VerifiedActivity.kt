package com.example.daweney.ui.verifiedaccount

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.daweney.R
import com.example.daweney.pojo.verifyaccount.VerifyUser
import com.example.daweney.ui.forgotpass.ForgotPassword
import com.example.daweney.ui.login.Login
import com.example.daweney.ui.register.Register
import com.example.daweney.ui.resetpass.ResetPassword
import com.example.daweney.ui.dialog.CustomDialogFragment
import kotlinx.android.synthetic.main.activity_verified.*

class VerifiedActivity : AppCompatActivity(), TextWatcher {
    private lateinit var verifyUser: VerifyUserViewModel
    private lateinit var sendCode: SendCodeViewModel
    private lateinit var callingActivity: String
    private lateinit var email: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verified)

        //watcher
        pinview.addTextChangedListener(this)

        verifyUser = ViewModelProvider(this)[VerifyUserViewModel::class.java]
        sendCode = ViewModelProvider(this)[SendCodeViewModel::class.java]


        getDataFormIntent()
        setEmailTextView()
        verifyUserProgressBar()
        verifySuccessful()
        dialogMessage()
        emailNotFound()
        codeSendSuccessful()
        sendCodeProgressBar()
        btn_verified_code.setOnClickListener { click(it) }
        resend.setOnClickListener { click(it) }

    }

    private fun setEmailTextView() {
        val emailTV: String = email.subSequence(0, 2).toString() + "**" + email.subSequence(
            email.length - 11,
            email.length
        )
        emailTextView.text = emailTV

    }

    private fun getDataFormIntent() {//get extra data form intent
        callingActivity = intent.getStringExtra("callingActivity").toString()
        when (callingActivity) {
            Register::class.java.toString() -> {
                email = intent.getStringExtra("email").toString()
                pass = intent.getStringExtra("pass").toString()
            }
            ForgotPassword::class.java.toString() -> {
                email = intent.getStringExtra("email").toString()
            }
            Login::class.java.toString() -> {
                email = intent.getStringExtra("email").toString()
                pass = intent.getStringExtra("pass").toString()
            }

        }
      //  Log.d("verified", email + pass)
    }

    private fun click(view: View?) {
         // progressBar.visibility = View.VISIBLE
        when (view?.id) {
            btn_verified_code.id -> {
                verifyUser.verifyUser(VerifyUser(pinview.text.toString(), email))
            }
            resend.id -> {
                sendCode.sendCode(email)
            }
        }
    }

    private fun verifyUserProgressBar() {
        verifyUser.progressBar.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun verifySuccessful() {
        verifyUser.verifyUserMutableLiveData.observe(this) {
            val intent: Intent
            when (callingActivity) {
                ForgotPassword::class.java.toString() -> {
                    intent = Intent(this, ResetPassword::class.java)
                    //send email to reset password activity
                    intent.putExtra("email", email)
                }
                Register::class.java.toString() -> {
                    intent = Intent(this, Login::class.java)

                    intent.putExtra("email", email)  //send email and pass to login
                    intent.putExtra("pass", pass)
                    Toast.makeText(this, "account create", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    intent = Intent(this, Login::class.java)
                    //send email and pass to login
                    intent.putExtra("email", email)
                    intent.putExtra("pass", pass)
                }
            }

            startActivity(intent)
            finish()

        }
    }

    private fun dialogMessage() {
        sendCode.dialogMessage.observe(this) {
            if (it.toString().isNotEmpty()) {
                val dialogFragment = CustomDialogFragment(it, applicationContext)
                dialogFragment.show(supportFragmentManager, "verify_error_dialog")
            }
        }
        verifyUser.dialogMessage.observe(this) {

            if (it.toString().isNotEmpty()) {
                val dialogFragment = CustomDialogFragment(it, applicationContext)
                dialogFragment.show(supportFragmentManager, "verify_error_dialog")
            }

        }
    }

    private fun emailNotFound() {
        sendCode.emailNotFound.observe(this) {
            val intent = Intent(this, VerifiedActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "you should register before", Toast.LENGTH_SHORT).show()
        }
    }

    private fun codeSendSuccessful() {
        sendCode.verifyUserMutableLiveData.observe(this) {
            Toast.makeText(this, "code has send", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendCodeProgressBar() {
        sendCode.progressBar.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        btn_verified_code.isEnabled = pinview.text?.trim()?.length == (4)
    }

    override fun afterTextChanged(s: Editable?) {

    }
}