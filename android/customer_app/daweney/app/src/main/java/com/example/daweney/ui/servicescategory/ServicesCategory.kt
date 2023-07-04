package com.example.daweney.ui.servicescategory

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.daweney.R
import com.example.daweney.pojo.intent_extra_key.IntentExtraKey
import com.example.daweney.pojo.services_category.ServicesCategoryResponse
import com.example.daweney.ui.dialog.CustomDialogFragment
import com.example.daweney.ui.sevices.Services
import kotlinx.android.synthetic.main.services_category.*

class ServicesCategory : AppCompatActivity() {
    private lateinit var servicesViewModel: ServicesCategoryViewModel
    private lateinit var services: ServicesCategoryResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.services_category)

        servicesViewModel = ViewModelProvider(this)[ServicesCategoryViewModel::class.java]


        progressBarObserver()
        dialogObserver()
        servicesCategoryObserver()
        onFailureObserver()
        getServicesCategory()
        onItemOFCategoryClick()
        refreshMyCategory.setOnRefreshListener { getServicesCategory() }
        backward.setOnClickListener { onClick(it) }
        setting.setOnClickListener { onClick(it) }
        try_again_btn.setOnClickListener { onClick(it) }

    }

    private fun progressBarObserver() {
        servicesViewModel.progressBar.observe(this) {
            if (it) {
                progressBar.visibility = ViewGroup.VISIBLE
            } else {
                progressBar.visibility = ViewGroup.INVISIBLE
            }
        }
    }

    private fun dialogObserver() {
        servicesViewModel.errorMessage.observe(this) {
            if (it != "") {
                val dialogFragment = CustomDialogFragment(it, applicationContext)
                dialogFragment.show(supportFragmentManager, "services_category_error_dialog")
            }
        }
    }


    private fun servicesCategoryObserver() {
        servicesViewModel.servicesCategory.observe(this) {
            services = it
            services_gridView.adapter = ServicesAdapter(this, services)
        }
    }


    private fun onFailureObserver() {
        servicesViewModel.failWithConnection.observe(this) {
            if (it) {
                noConnectionErrorContainer.visibility = ViewGroup.VISIBLE
                noConnectionAnimationView.visibility = ViewGroup.VISIBLE
                services_gridView.visibility = ViewGroup.GONE
                noConnectionAnimationView.setAnimation(R.raw.no_connection)
                noConnectionAnimationView.playAnimation()
            } else {
                noConnectionErrorContainer.visibility = ViewGroup.GONE
                noConnectionAnimationView.visibility = ViewGroup.GONE
                services_gridView.visibility = ViewGroup.VISIBLE

            }
        }
    }


    private fun getServicesCategory() {
        servicesViewModel.getServicesCategory()
        refreshMyCategory.isRefreshing = false
    }


    private fun onItemOFCategoryClick() {
        services_gridView.onItemClickListener = (
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val intent = Intent(this, Services::class.java)
                    intent.putExtra(IntentExtraKey.SERVICE_TYPE, services[position].EnglishName)
                    startActivity(intent)
                })
    }


    private fun onClick(view: View?) {
        when (view?.id) {
            backward.id -> {
                finish()
            }
            setting?.id -> {
                Toast.makeText(this, "soon", Toast.LENGTH_SHORT).show()
            }
            try_again_btn.id -> {
                getServicesCategory()
            }

        }
    }


}