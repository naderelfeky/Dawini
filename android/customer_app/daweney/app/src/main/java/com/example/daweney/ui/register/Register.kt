package com.example.daweney.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.daweney.R
import com.example.daweney.pojo.register.RegisterUser
import com.example.daweney.ui.login.Login
import com.example.daweney.ui.verifiedaccount.SendCodeViewModel
import com.example.daweney.ui.dialog.CustomDialogFragment
import com.example.daweney.ui.verifiedaccount.VerifiedActivity
import kotlinx.android.synthetic.main.register.*

class Register : AppCompatActivity() , TextWatcher {
    private lateinit var register: RegisterViewModel
    private lateinit var sendCode: SendCodeViewModel
    private lateinit var userName:String
    private lateinit var email:String
    private lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        //watcher
        userNameEditText.addTextChangedListener(this)
        emailEditText.addTextChangedListener(this)
        passwordEditText.addTextChangedListener(this)

        register= ViewModelProvider(this)[RegisterViewModel::class.java]
        sendCode= ViewModelProvider(this)[SendCodeViewModel::class.java]

        signUpButton.setOnClickListener { click(it) }
        login.setOnClickListener{click(it) }
        progressBarObserve()
        dialogMessageObserve()
        registerSuccessful()

    }



    private fun click(view: View?) {
        when(view?.id){
            R.id.signUpButton->{
                //get text and assign in variable
                userName=userNameEditText.text?.trim().toString()
                email=emailEditText.text?.trim().toString()
                password=passwordEditText.text?.trim().toString()

                register.register(RegisterUser(userName,email,password))

                progressBar.visibility= View.VISIBLE
                userNameEditText.isEnabled=false
                emailEditText.isEnabled=false
                passwordEditText.isEnabled=false

            }
           R.id.login->{
               startLoginActivity()
           }
        }


    }


    private fun dialogMessageObserve(){
        register.dialogMessage.observe(this) {
            if (it.toString().isNotEmpty()){
                val dialogFragment = CustomDialogFragment(it,applicationContext)
                dialogFragment.show(supportFragmentManager, "register_error_dialog")
            }
        }

    }
    private fun progressBarObserve(){
        register.progressBar.observe(this){
            if(it){
                progressBar.visibility= View.VISIBLE
                signUpButton.isEnabled=false
            }else{
                progressBar.visibility= View.INVISIBLE
                userNameEditText.isEnabled=true
                emailEditText.isEnabled=true
                passwordEditText.isEnabled=true
                signUpButton.isEnabled=true
            }
        }
    }


    private fun startLoginActivity(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerSuccessful() {

        register.registerMutableLiveData.observe(this){
            sendCode.sendCode(email)
            val intent = Intent(this, VerifiedActivity::class.java)
            intent.putExtra("callingActivity", Register::class.java.toString())
            intent.putExtra("email",email)
            intent.putExtra("pass",password)
            startActivity(intent)
            finish()
        }
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        signUpButton.isEnabled = (userNameEditText.text.toString().trim().isNotEmpty() &&
                                    emailEditText.text.toString().trim().isNotEmpty()  &&
                                    passwordEditText.text.toString().trim().isNotEmpty())

    }

    override fun afterTextChanged(s: Editable?) {

    }
}