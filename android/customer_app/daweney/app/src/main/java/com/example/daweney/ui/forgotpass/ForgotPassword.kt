package com.example.daweney.ui.forgotpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.daweney.R
import com.example.daweney.ui.dialog.CustomDialogFragment
import com.example.daweney.ui.verifiedaccount.VerifiedActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity(),TextWatcher {
    private lateinit var sendCode: SendCodeViewModel
    private lateinit var email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        //watcher
        emailEditText.addTextChangedListener(this)

        sendCode= ViewModelProvider(this)[SendCodeViewModel::class.java]

        sendCodeButton.setOnClickListener {
            email=emailEditText.text.toString()
            sendCode.sendCode(email)
            Log.d("resetcode",email)
        }
        getDataFormIntent()
        dialogMessage()
        codeSendSuccessful()
        sendCodeProgressBar()
    }
    private fun getDataFormIntent() {//get extra data form intent
        if(intent.getStringExtra("email").toString()!="null")
           emailEditText.setText(intent.getStringExtra("email").toString())
    }
    private fun sendCodeProgressBar(){
        sendCode.progressBar.observe(this){
            if(it){
                progressBar.visibility= View.VISIBLE
                sendCodeButton.isEnabled=false
            }else{
                progressBar.visibility= View.INVISIBLE
                sendCodeButton.isEnabled=true
            }
        }
    }


    private fun codeSendSuccessful(){
        sendCode.verifyUserMutableLiveData.observe(this){
            Toast.makeText(this,"code has send", Toast.LENGTH_SHORT).show()
            val intent= Intent(this , VerifiedActivity::class.java)
            intent.putExtra("email",email)
            intent.putExtra("callingActivity", ForgotPassword::class.java.toString())
            startActivity(intent)
            finish()
        }
    }


    private fun dialogMessage(){
        sendCode.dialogMessage.observe(this){
            if (it.toString().isNotEmpty()){
                val dialogFragment = CustomDialogFragment(it,applicationContext)
                dialogFragment.show(supportFragmentManager, "send_code_error_dialog")
            }
        }

    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        sendCodeButton.isEnabled=emailEditText.text?.trim()?.isNotEmpty()==true
    }

    override fun afterTextChanged(s: Editable?) {

    }
}