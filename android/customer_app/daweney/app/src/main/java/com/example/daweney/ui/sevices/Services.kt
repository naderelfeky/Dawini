package com.example.daweney.ui.sevices

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daweney.R
import com.example.daweney.pojo.intent_extra_key.IntentExtraKey
import com.example.daweney.pojo.services.ServicesBody
import com.example.daweney.ui.dialog.CustomDialogFragment
import kotlinx.android.synthetic.main.activity_services.*

class Services : AppCompatActivity() {
    private lateinit var servicesViewModel: ServicesViewModel
    private lateinit var servicesRecyclerView: RecyclerView
    private lateinit var servicesAdapter: ServicesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        servicesViewModel = ViewModelProvider(this)[ServicesViewModel::class.java]

        getServices()
        recyclerViewInit()
        btn_continue.setOnClickListener { click(it) }
        getServicesObserver()
        progressBarObserver()
        onFailureObserver()
        dialogObserver()
        searcherChangeListener()
        toolbarIconSearch.setOnClickListener { onClick(it) }
        try_again_btn.setOnClickListener { onClick(it) }
        refreshServices.setOnRefreshListener { getServices() }
        refreshServicesList.setOnRefreshListener { getServices() }
    }

    private fun searcherChangeListener() {
        toolbarSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (::servicesAdapter.isInitialized) servicesAdapter.filter.filter(toolbarSearch.text)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun onFailureObserver() {
        servicesViewModel.failWithConnection.observe(this) {
            if (it) {
                servicesRecyclerView.visibility = ViewGroup.GONE
                serviceNoConnectionAnimationView.visibility = ViewGroup.VISIBLE
                messageContainer.visibility = ViewGroup.VISIBLE
                serviceNoConnectionAnimationView.setAnimation(R.raw.no_connection)
                serviceNoConnectionAnimationView.playAnimation()


            } else {

                servicesRecyclerView.visibility = ViewGroup.VISIBLE
                serviceNoConnectionAnimationView.visibility = ViewGroup.GONE
                messageContainer.visibility = ViewGroup.GONE

            }
        }
    }

    private fun onClick(view: View?) {
        when (view?.id) {
            R.id.toolbarIconSearch -> {
                if (searchContainer.width == resources.getDimensionPixelSize(R.dimen.icon_size)) {
                    openSearchBar()
                } else {
                    closeSearchBar()
                }
            }
            R.id.try_again_btn -> {
                getServices()
            }
        }
    }

    private fun closeSearchBar() {
        val targetWidth = resources.getDimensionPixelSize(R.dimen.icon_size)
        val animator = ValueAnimator.ofInt(searchContainer.width, targetWidth)
        animator.addUpdateListener { valueAnimator ->
            val layoutParams = searchContainer.layoutParams
            layoutParams.width = valueAnimator.animatedValue as Int
            searchContainer.requestLayout()
        }
        animator.duration = 400 // Set the duration of the animation (in milliseconds)
        animator.start()
        toolbarIconSearch.setImageResource(R.drawable.ic_search)
    }


    private fun openSearchBar() {
        val displayMetrics = resources.displayMetrics
        val deviceWidth = displayMetrics.widthPixels

        val density = displayMetrics.density
        val dpToPx = 80 * density

        val targetWidth = deviceWidth - dpToPx.toInt()
        val animator = ValueAnimator.ofInt(searchContainer.width, targetWidth)
        animator.addUpdateListener { valueAnimator ->
            val layoutParams = searchContainer.layoutParams
            layoutParams.width = valueAnimator.animatedValue as Int
            searchContainer.requestLayout()
        }
        animator.duration = 400 // Set the duration of the animation (in milliseconds)
        animator.start()
        toolbarIconSearch.setImageResource(R.drawable.ic_left)
    }


    private fun getServices() {
        servicesViewModel.getServices(gerServiceType())
        refreshServices.isRefreshing = false
        refreshServicesList.isRefreshing = false
    }

    private fun recyclerViewInit() {
        servicesRecyclerView = findViewById(R.id.servicesRecyclerView)
        servicesRecyclerView.setHasFixedSize(true)
        servicesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun click(view: View?) {
        when (view) {
            btn_continue -> {
                Toast.makeText(this, "fd", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getServicesObserver() {
        servicesViewModel.services.observe(this) {
            if (it != null) {

                servicesAdapter = ServicesAdapter(it)
                servicesRecyclerView.adapter = servicesAdapter

            }
        }
    }

    private fun gerServiceType(): ServicesBody {
        val serviceType = intent.getStringExtra(IntentExtraKey.SERVICE_TYPE).toString()
        Toast.makeText(this, serviceType, Toast.LENGTH_SHORT).show()
        //ToDo nnnnnanannnan
        return ServicesBody("nurse")
    }


    private fun dialogObserver() {
        servicesViewModel.errorMessage.observe(this) {
            if (it != "") {
                val dialogFragment = CustomDialogFragment(it, applicationContext)
                dialogFragment.show(supportFragmentManager, "services_error_dialog")
            }
        }
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
}