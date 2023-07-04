package com.example.daweney.ui.myrequestdetails

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.daweney.R
import com.example.daweney.pojo.intent_extra_key.IntentExtraKey
import com.example.daweney.pojo.myreqests.RequestResponseItem
import java.text.SimpleDateFormat
import java.util.*


class MyRequestDetails : AppCompatActivity() {
    lateinit var serviceType: TextView
    lateinit var date: TextView
    lateinit var address: TextView
    lateinit var cost: TextView
    lateinit var providerGender: TextView
    lateinit var providerName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_request_details)
        initialization()
        getDataFormIntent()
    }

    private fun initialization() {
        date = findViewById<TextView>(R.id.requestDate)
        address = findViewById<TextView>(R.id.requestAddress)
        cost = findViewById<TextView>(R.id.requestCost)
        providerGender = findViewById<TextView>(R.id.requestProviderType)
        providerName = findViewById<TextView>(R.id.requestProviderName)
        serviceType = findViewById<TextView>(R.id.serviceType)
    }

    private fun getDataFormIntent() {
        val intentData = intent.getParcelableExtra<RequestResponseItem>(IntentExtraKey.MY_REQUEST)
        serviceType.text=intentData?.typeofservice
        date.text=getDate(intentData!!.date)
        address.text=intentData.address
        cost.text=intentData.priceOfService.toString()
    }



    private fun getDate(dateString: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date: Date = format.parse(dateString) as Date
        val dateFormat = SimpleDateFormat("hh:mm a - yy/MMM/dd", Locale.getDefault())
        return dateFormat.format(date)
    }
}