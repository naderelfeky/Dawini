package com.example.daweney.ui.sendrequest

import android.animation.Animator
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.daweney.R
import com.example.daweney.pojo.intent_extra_key.IntentExtraKey
import com.example.daweney.pojo.send_request.SendRequestBody
import com.example.daweney.pojo.services.ServicesResponse
import com.example.daweney.pojo.services.ServicesResponseItem
import com.example.daweney.repo.SharedPrefRepo
import com.example.daweney.ui.dialog.CustomDialogFragment
import com.example.daweney.ui.login.Login
import com.example.daweney.ui.myrequests.MyRequests
import com.example.daweney.ui.sharedPref.SharedPrefVM
import com.example.daweney.ui.sharedPref.SharedPreferenceRepoFactory
import kotlinx.android.synthetic.main.activity_send_request.*
import java.text.SimpleDateFormat
import java.util.*

class SendRequest : AppCompatActivity() {

    private lateinit var dateEditText: EditText
    private lateinit var genderEditView: AutoCompleteTextView
    private lateinit var servicesAdapter: ServicesAdapter
    private lateinit var selectionServicesRecyclerView: RecyclerView
    private lateinit var patientNameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var sendRequestCardView: CardView
    private lateinit var sendRequestAnimation: LottieAnimationView
    private var servicesList: ArrayList<ServicesResponseItem>? = null
    private lateinit var sendRequestViewModel: SendRequestViewModel
    private lateinit var sharedPrefVM: SharedPrefVM
    private lateinit var date: String
    private lateinit var serviceType: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_request)

        sendRequestViewModel = ViewModelProvider(this)[SendRequestViewModel::class.java]
        //SharedPreference
        val myArg = SharedPrefRepo(this)
        val factory = SharedPreferenceRepoFactory(myArg)
        sharedPrefVM = ViewModelProvider(this, factory)[SharedPrefVM::class.java]

        initialization()
        initGenderMenu()
        sendRequestSuccessObserver()
        sendRequestFailureObserver()
        sendRequestNoProviderObserver()
        sendRequestErrorObserver()
        progressBarObserver()
        getServicesFormIntent()
        patientNameTextWatcher()
        phoneTextWatcher()
        dateTextWatcher()
        addressTextWatcher()
        locationTextWatcher()
        dateEditText.setOnClickListener { onClick(it) }
        locationEditText.setOnClickListener { onClick(it) }
        genderEditView.setOnClickListener { onClick(it) }
        sendButton.setOnClickListener { onClick(it) }


    }

    private fun progressBarObserver() {
        sendRequestViewModel.progressBar.observe(this) {
            if (it) {
                progressBar.visibility = ViewGroup.VISIBLE
            } else {
                progressBar.visibility = ViewGroup.GONE
            }
        }
    }

    private fun sendRequestErrorObserver() {
        sendRequestViewModel.sendRequestInvalidError.observe(this) {
            if (it) {
                val dialogFragment = CustomDialogFragment(
                    getString(R.string.invalid_data_entered),
                    applicationContext
                )
                dialogFragment.show(supportFragmentManager, "send_request_error_dialog")
            }
        }
    }

    private fun sendRequestNoProviderObserver() {
        sendRequestViewModel.sendRequestNoProvider.observe(this) {
            if (it) {
                val dialogFragment =
                    CustomDialogFragment(getString(R.string.no_provider), applicationContext)
                dialogFragment.show(supportFragmentManager, "send_request_No_provider_error_dialog")
            }
        }
    }

    private fun sendRequestSuccessObserver() {
        sendRequestViewModel.sendRequestSuccess.observe(this) {
            if (it) {
                sendRequestCardView.visibility = ViewGroup.GONE
                sendRequestAnimation.visibility = ViewGroup.VISIBLE
                sendRequestAnimation.setAnimation(R.raw.send_animation)
                sendRequestAnimation.playAnimation()
                sendRequestAnimation.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        val intent = Intent(this@SendRequest, MyRequests::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finishAffinity()
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })

            }
        }
    }

    private fun sendRequestFailureObserver() {
        sendRequestViewModel.sendRequestFailure.observe(this) {
            if (it) {
                val dialogFragment = CustomDialogFragment(
                    getString(R.string.error_with_connection),
                    applicationContext
                )
                dialogFragment.show(supportFragmentManager, "send_request_failure_dialog")

            }
        }
    }

    private fun patientNameTextWatcher() {
        patientNameTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {

                    patientNameTextView.error = getString(R.string.name_required_msg)
                } else {

                    if (isValidName(s.toString())) {
                        patientNameTextView.error = null
                    } else {
                        patientNameTextView.error = getString(R.string.name_not_valid)
                    }

                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun isValidName(name: String): Boolean {
        val regex =
            Regex("^[\u0621-\u064Aa-zA-Z]+(([',. -][\u0621-\u064Aa-zA-Z ])?[\u0621-\u064Aa-zA-Z]*)*[\u0621-\u064Aa-zA-Z ]?\$")
        return regex.matches(name)
    }

    private fun phoneTextWatcher() {
        phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {

                    phoneEditText.error = getString(R.string.phone_required_msg)
                } else {
                    if ((s.length == 11 && s[0] == '0') || (s.length == 10 && s[0] != '0')) {
                        phoneEditText.error = null
                    } else {
                        phoneEditText.error = getString(R.string.phone_not_valid)
                    }

                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun dateTextWatcher() {
        dateEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    dateEditText.error = getString(R.string.date_required_msg)
                } else {
                    dateEditText.error = null
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun addressTextWatcher() {
        addressEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    addressEditText.error = getString(R.string.address_required_msg)
                } else {
                    addressEditText.error = null
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun locationTextWatcher() {
        locationEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    locationEditText.error = getString(R.string.address_required_msg)
                } else {
                    locationEditText.error = null
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun getServicesFormIntent() {
        servicesList =
            intent.getSerializableExtra(IntentExtraKey.SERVICES) as? ArrayList<ServicesResponseItem>

        if (servicesList != null) {
            val selectionServices = servicesList!!.toCollection(ServicesResponse())
            servicesAdapter = ServicesAdapter(selectionServices)
            selectionServicesRecyclerView.layoutManager = LinearLayoutManager(this)
            selectionServicesRecyclerView.adapter = servicesAdapter
        }
        serviceType = intent.getStringExtra(IntentExtraKey.SERVICE_TYPE).toString()
    }

    private fun initGenderMenu() {

        val menuInflater = MenuInflater(this)
        val menu = PopupMenu(this, null).menu
        menuInflater.inflate(R.menu.gender_menu_spinner, menu)
        val itemList = mutableListOf<String>()
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            itemList.add(item.title.toString())
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, itemList)
        genderTextView.setAdapter(adapter)
        genderTextView.setText(itemList[0], false)
    }

    private fun initialization() {
        selectionServicesRecyclerView = findViewById(R.id.selectionServicesRecyclerView)
        dateEditText = findViewById(R.id.dateEditText)
        genderEditView = findViewById(R.id.genderTextView)
        patientNameEditText = findViewById(R.id.patientNameTextView)
        phoneEditText = findViewById(R.id.phoneNumberTextView)
        addressEditText = findViewById(R.id.requestAddressTextView)
        locationEditText = findViewById(R.id.requestLocationTextView)
        sendButton = findViewById(R.id.sendRequest)
        progressBar = findViewById(R.id.progressBar)
        sendRequestCardView = findViewById(R.id.sendRequestCardView)
        sendRequestAnimation = findViewById(R.id.sendRequestAnimation)
    }

    private fun onClick(view: View?) {
        when (view?.id) {
            R.id.dateEditText -> {
                showDatePickerDialog()
            }
            R.id.genderTextView -> {
                genderEditView.showDropDown()
            }
            R.id.sendRequest -> {
                if (allFieldValid()) sendRequestViewModel.sendRequest(getRequest())

            }
            R.id.requestLocationTextView->{

            }
        }
    }


    private fun allFieldValid(): Boolean {
        if (allFieldNotEmpty()) {

            if (patientNameTextView.error != null) {
                showToast(getString(R.string.name_not_valid))
            } else if (phoneEditText.error != null) {
                showToast(getString(R.string.phone_not_valid))
            } else if (dateEditText.error != null) {
                showToast(getString(R.string.date_not_valid))
            } else if (addressEditText.error != null) {
                showToast(getString(R.string.address_required_msg))
            } else if (locationEditText.error != null) {
                showToast(getString(R.string.address_required_msg))
            } else {
                return true
            }
        } else {
            return false
        }
        return false
    }

    private fun allFieldNotEmpty(): Boolean {

        if (patientNameTextView.text.isNullOrEmpty()) {
            patientNameTextView.error = getString(R.string.name_required_msg)
        } else if (phoneEditText.text.isNullOrEmpty()) {
            phoneEditText.error = getString(R.string.phone_required_msg)
        } else if (dateEditText.text.isNullOrEmpty()) {
            dateEditText.error = getString(R.string.date_required_msg)
        } else if (addressEditText.text.isNullOrEmpty()) {
            addressEditText.error = getString(R.string.address_required_msg)
        }
//        else if (locationTextView.text.isNullOrEmpty()) {
//            locationTextView.error = getString(R.string.address_required_msg)
//        }
        else {
            return true
        }
        return false
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun getRequest(): SendRequestBody {
        return SendRequestBody(
            addressEditText.text.toString(), getCustomerId(),
            getProviderGender(), 30.941733, 30.680024, patientNameTextView.text.toString(),
            phoneEditText.text.toString(), getServicesList(), date, serviceType
        )
    }

    private fun getProviderGender(): String {
        return when (genderEditView.text.toString()) {
            getString(R.string.female) -> {
                "female"
            }
            getString(R.string.male) -> {
                "male"
            }
            else -> {
                "anyone"
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DateAndTimeDialog,
            { _, year, month, dayOfMonth ->
                // Check if the selected date is in the past
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val currentDate = Calendar.getInstance()
                if (selectedDate.before(currentDate)) {
                    // Show an error message or prevent further action
                    Toast.makeText(this, getString(R.string.select_future_date), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    showTimePickerDialog(year, month, dayOfMonth)
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun showTimePickerDialog(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            R.style.DateAndTimeDialog,
            { _, hourOfDay, minute ->
                // Check if the selected time is in the past
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth, hourOfDay, minute)
                val currentDate = Calendar.getInstance()
                val timeDifferenceMinutes =
                    (selectedDate.timeInMillis - currentDate.timeInMillis) / (1000 * 60)

                if (selectedDate.before(currentDate)) {
                    // Show an error message or prevent further action
                    Toast.makeText(this, getString(R.string.select_future_time), Toast.LENGTH_SHORT)
                        .show()
                } else if (timeDifferenceMinutes < 30) {
                    // Show a different error message if time is less than 30 minutes from current time
                    Toast.makeText(
                        this,
                        getString(R.string.select_time_more_than_30m),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val dateFormat = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
                    val requestDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("en"))
                    date = requestDateFormat.format(selectedDate.time)
                    val formattedDate = dateFormat.format(selectedDate.time)
                    dateEditText.setText(formattedDate)
                }
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    private fun getCustomerId(): String {
        val customerId = sharedPrefVM.getData(SharedPrefRepo.CUSTOMER_ID, "").toString()
        if (customerId.isEmpty()) {
            logout()
        }
        return customerId
    }

    private fun logout() {
        val intent = Intent(this, Login::class.java)
        sharedPrefVM.deleteDate(getCustomerId())
        startActivity(intent)
        finish()
    }

    private fun getServicesList(): List<String> {
        val servicesIds = ArrayList<String>()
        for (service in servicesList!!) {
            servicesIds.add(service._id)
        }
        return servicesIds
    }


}