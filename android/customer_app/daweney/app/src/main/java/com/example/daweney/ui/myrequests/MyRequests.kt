package com.example.daweney.ui.myrequests


import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daweney.R
import com.example.daweney.pojo.intent_extra_key.IntentExtraKey
import com.example.daweney.pojo.myreqests.RequestBody
import com.example.daweney.pojo.myreqests.RequestResponseItem
import com.example.daweney.repo.SharedPrefRepo
import com.example.daweney.ui.login.Login
import com.example.daweney.ui.myrequestdetails.MyRequestDetails
import com.example.daweney.ui.servicescategory.ServicesCategory
import com.example.daweney.ui.sharedPref.SharedPrefVM
import com.example.daweney.ui.sharedPref.SharedPreferenceRepoFactory
import kotlinx.android.synthetic.main.activity_my_requests.*

class MyRequests : AppCompatActivity() ,MyRequestInterface {
    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    lateinit var requestAdapter: RequestRecyclerViewAdapter
    private lateinit var sharedPrefVM: SharedPrefVM
    private var myRequestsViewModel = MyRequestsViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_requests)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        // recyclerView
        recyclerView = findViewById(R.id.requestRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //SharedPreference
        val myArg = SharedPrefRepo(this)
        val factory = SharedPreferenceRepoFactory(myArg)
        sharedPrefVM = ViewModelProvider(this, factory)[SharedPrefVM::class.java]

        //view model
        myRequestsViewModel = ViewModelProvider(this)[MyRequestsViewModel::class.java]

        //call get request
        myRequestsViewModel.getMyRequests(RequestBody(getCustomerId()))


        myRequestObserver()
        onEmptyRequestListObserver()
        onFailureObserver()
        searcherChangeListener()
        progressBarObserve()
        toolbarIconSearch.setOnClickListener { onClick(it) }
        addRequest.setOnClickListener { onClick(it) }
        try_again_btn.setOnClickListener { onClick(it) }
        refreshMyRequest()

    }

    private fun onEmptyRequestListObserver() {
        myRequestsViewModel.emptyRequest.observe(this) {
            recyclerView.visibility = View.GONE
            myRequestLottieAnimationView.visibility = View.VISIBLE
            messageContainer.visibility = View.VISIBLE
            try_again_btn.visibility = View.GONE
            messageTV.text = getString(R.string.request_new_service)
            messageLabel.setText(getString(R.string.empty_request_list))
            myRequestLottieAnimationView.setAnimation(R.raw.empty_request)
            myRequestLottieAnimationView.playAnimation()
        }

    }

    private fun onFailureObserver() {
        myRequestsViewModel.failure.observe(this) {
            recyclerView.visibility = View.GONE
            myRequestLottieAnimationView.visibility = View.VISIBLE
            messageContainer.visibility = View.VISIBLE
            try_again_btn.visibility = View.VISIBLE
            messageLabel.setText(getString(R.string.oops))
            messageTV.text = getString(R.string.error_with_connection)

            myRequestLottieAnimationView.setAnimation(R.raw.no_connection)
            myRequestLottieAnimationView.playAnimation()
        }
    }

    private fun logout() {
        val intent = Intent(this, Login::class.java)
        sharedPrefVM.deleteDate(getCustomerId())
        startActivity(intent)
    }

    private fun getCustomerId(): String {
        val customerId = sharedPrefVM.getData(SharedPrefRepo.CUSTOMER_ID, "").toString()
        if (customerId.isEmpty()) {
            logout()
        }
        return customerId
    }

    private fun searcherChangeListener() {

        toolbarSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if(::requestAdapter.isInitialized)
                requestAdapter.filter.filter(toolbarSearch.text)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun myRequestObserver() {

        myRequestsViewModel.myRequestsResponse.observe(this) {

            recyclerView.visibility = View.VISIBLE
            myRequestLottieAnimationView.visibility = View.GONE
            try_again_btn.visibility = View.GONE
            messageContainer.visibility = View.GONE
            requestAdapter = RequestRecyclerViewAdapter(it,this)
            recyclerView.adapter = requestAdapter
        }
    }

    private fun progressBarObserve() {
        myRequestsViewModel.progressBar.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun refreshMyRequest() {
        refreshMyRequest.setOnRefreshListener {  //refresh when my list id empty or network error
            myRequestsViewModel.getMyRequests(RequestBody(getCustomerId()))
            refreshMyRequest.isRefreshing = false
        }
        refreshMyRequestList.setOnRefreshListener {  //refresh when my list no empty
            myRequestsViewModel.getMyRequests(RequestBody(getCustomerId()))
            refreshMyRequestList.isRefreshing = false
        }
    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.toolbarIconSearch -> {
                if (searchContainer.width == resources.getDimensionPixelSize(R.dimen.icon_size)) {
                    openSearchBar()
                } else {
                    closeSearchBar()
                }
            }
            R.id.addRequest -> {
                val intent = Intent(this, ServicesCategory::class.java)
                startActivity(intent)
            }
            R.id.try_again_btn -> {
                myRequestsViewModel.getMyRequests(RequestBody(getCustomerId()))
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

    override fun onItemClick(request: RequestResponseItem) {
        val intent=Intent(this,MyRequestDetails::class.java)
        intent.putExtra(IntentExtraKey.MY_REQUEST,request)
        Toast.makeText(this,request.date,Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

    override fun onItemLongClick(request: RequestResponseItem) {
        val intent=Intent(this,MyRequestDetails::class.java)
        intent.putExtra(IntentExtraKey.MY_REQUEST,request)
        startActivity(intent)
    }


}


