package com.example.daweney.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.daweney.R
import com.example.daweney.R.*
import com.example.daweney.pojo.dataclass.LoginUser
import com.example.daweney.repo.SharedPrefRepo
import com.example.daweney.viewmodel.LoginViewModel
import com.example.daweney.viewmodel.SendCodeViewModel
import com.example.daweney.viewmodel.SharedPrefVM
import com.example.daweney.viewmodel.SharedPreferenceRepoFactory
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity(), TextWatcher {

    private lateinit var sharedPrefVM: SharedPrefVM
    private lateinit var login: LoginViewModel
    private lateinit var sendCode: SendCodeViewModel
    private lateinit var email: String
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.login)


        //watcher
        emailTextView.addTextChangedListener(this)
        passwordTextView.addTextChangedListener(this)

        val myArg = SharedPrefRepo(this)
        val factory = SharedPreferenceRepoFactory(myArg)

        login = ViewModelProvider(this)[LoginViewModel::class.java]
        sharedPrefVM = ViewModelProvider(this, factory)[SharedPrefVM::class.java]
        sendCode=ViewModelProvider(this)[SendCodeViewModel::class.java]

        sharedPrefVM.saveData(SharedPrefRepo.EMAIL, "null")
        if (sharedPrefVM.getData(SharedPrefRepo.EMAIL, "null") != "null") {
            startMainActivity()
        }
        loginButton.setOnClickListener { click(it) }
        signup.setOnClickListener { click(it) }
        forget_pass.setOnClickListener { click(it) }
        getIntentExtrasData()
        loginSuccessful()
        progressBarObserve()
        dialogMessageObserve()
        emailVerifiedObserve()
    }

    private fun getIntentExtrasData() {
        var mail: String = intent.getStringExtra("email").toString()
        var pass: String = intent.getStringExtra("pass").toString()
        Log.d("extras", pass + mail)
        if (mail != "null") emailTextView.setText(mail)
        if (pass != "null") passwordTextView.setText(pass)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        loginButton.isEnabled = (emailTextView.text?.trim()?.isNotEmpty() == true
                && passwordTextView.text?.trim()?.isNotEmpty() == true)
    }

    override fun afterTextChanged(s: Editable?) {

    }

    private fun startMainActivity() {

        val intent = Intent(this, ServicesActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startVerifiedCodeActivity() {
        sendCode.sendCode(email)
        val intent = Intent(this, VerifiedActivity::class.java)
        intent.putExtra("callingActivity",Login::getLocalClassName.toString())
        intent.putExtra("email",email)
        intent.putExtra("pass",password)
        Log.d("verified",Login::getLocalClassName.toString() +email)
        startActivity(intent)
        finish()
    }

    private fun startRegisterActivity() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
        finish()
    }

    private fun click(view: View?) {
        when (view?.id) {
            R.id.loginButton -> {

                email = emailTextView.text?.trim().toString()
                password = passwordTextView.text?.trim().toString()

                login.login(LoginUser(email, password))

                progressBar.visibility = View.VISIBLE
                emailTextView.isEnabled = false
                passwordTextView.isEnabled = false

            }
            R.id.signup -> {
                startRegisterActivity()
            }
            R.id.forget_pass -> {
                val intent = Intent(applicationContext, ForgotPassword::class.java)
                if(::email.isInitialized) intent.putExtra("email",email)
                startActivity(intent)
            }
        }


    }

    private fun emailVerifiedObserve() {
        login.emailIsVerified.observe(this) {

            startVerifiedCodeActivity()

        }
    }

    private fun dialogMessageObserve() {
        login.dialogMessage.observe(this) {

            if (it.toString().isNotEmpty()) {
                val dialogFragment = CustomDialogFragment(it, applicationContext)
                dialogFragment.show(supportFragmentManager, "login_error_dialog")
            }

        }

    }

    private fun progressBarObserve() {
        login.progressBar.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
                loginButton.isEnabled = false
            } else {
                progressBar.visibility = View.INVISIBLE
                emailTextView.isEnabled = true
                passwordTextView.isEnabled = true
                loginButton.isEnabled = true
            }
        }
    }

    private fun loginSuccessful() {
        login.loginMutableLiveData.observe(this) {

            sharedPrefVM.saveData(SharedPrefRepo.EMAIL, email)
            sharedPrefVM.saveData(SharedPrefRepo.PASS, password)
            startMainActivity()
            //  Log.d("else", sharedPrefVM.getData(SharedPrefRepo.EMAIL,"null").toString())
        }

    }
}