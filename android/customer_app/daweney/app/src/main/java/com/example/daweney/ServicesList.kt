package com.example.daweney

object ServicesList {
    var  servicesList:ArrayList<Services>?=ArrayList<Services>()
    init {
        servicesList?.add(Services("nursing services",R.drawable.doctor))
        servicesList?.add(Services("medical services", R.drawable.doctor))
        servicesList?.add(Services("radiology", R.drawable.doctor))
        servicesList?.add(Services("medical tests", R.drawable.doctor))
        servicesList?.add(Services("medical supplies", R.drawable.doctor))
        servicesList?.add(Services("Complaints and inquiries", R.drawable.doctor))
    }
}