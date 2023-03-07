package com.example.daweney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import kotlinx.android.synthetic.main.sub_services_activity.*

class SubServicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sub_services_activity)

        subServices_listView.adapter=SubServicesAdapter(this ,SubServicesList.nurseSubServicesList)

        subServices_listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
//                var v=view as TextView
//                Toast.makeText(applicationContext,v.text.toString(),Toast.LENGTH_SHORT).show()
            }
    }
}