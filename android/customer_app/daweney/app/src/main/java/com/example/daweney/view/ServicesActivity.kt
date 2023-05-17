package com.example.daweney.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.example.daweney.R
import com.example.daweney.ServicesAdapter
import kotlinx.android.synthetic.main.services_activity.*

class ServicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.services_activity)
        services_gridView.adapter= ServicesAdapter(this )
        services_gridView.onItemClickListener=(AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent= Intent(applicationContext, SubServicesActivity::class.java)
            intent.putExtra("index" ,position.toInt())
            startActivity(intent)
        })
    }
}