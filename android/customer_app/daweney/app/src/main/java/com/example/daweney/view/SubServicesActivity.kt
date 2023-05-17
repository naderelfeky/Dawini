package com.example.daweney.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.example.daweney.R
import com.example.daweney.SubServicesAdapter
import com.example.daweney.SubServicesList
import kotlinx.android.synthetic.main.sub_services_activity.*

class SubServicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sub_services_activity)
        subServices_listView.adapter=when(intent.extras?.getInt("index")){
            0 -> SubServicesAdapter(this , SubServicesList.nurseSubServicesList)
            1 -> SubServicesAdapter(this , SubServicesList.medicalSubServicesList)
            else -> {
                Toast.makeText(applicationContext, "will add soon 😁 ${intent.extras?.getInt("index")}",Toast.LENGTH_SHORT).show()
                SubServicesAdapter(this , arrayListOf())
            }

        }
        backward.setOnClickListener { finish() }

        subServices_listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
//                var v=view as TextView
//                Toast.makeText(applicationContext,v.text.toString(),Toast.LENGTH_SHORT).show()
            }
    }
}