package com.example.daweney
// to use single_ton
object SubServicesList {
    var nurseSubServicesList= arrayListOf<SubServices>()
    var medicalSubServicesList= arrayListOf<SubServices>( )
    init {
        val  nurseList= arrayListOf<SubServices>(
            SubServices("service1",R.drawable.doctor),
            SubServices("service2",R.drawable.doctor),
            SubServices("service3",R.drawable.doctor),
            SubServices("service4",R.drawable.doctor),
            SubServices("service5",R.drawable.doctor),
            SubServices("service6",R.drawable.doctor),
            SubServices("service7",R.drawable.doctor),
            SubServices("service8",R.drawable.doctor),
            SubServices("service8888888888888 8888888888 888888  888888888888888888",R.drawable.doctor),
            SubServices("service1",R.drawable.doctor),
            SubServices("service2",R.drawable.doctor),
            SubServices("service3",R.drawable.doctor),
            SubServices("service4",R.drawable.doctor),
            SubServices("service5",R.drawable.doctor),
            SubServices("service6",R.drawable.doctor),
            SubServices("service7",R.drawable.doctor),
            SubServices("service8",R.drawable.doctor),
            SubServices("service888888888888888888888888888888888888888888888888888",R.drawable.doctor)
        )
        val medicalList= arrayListOf<SubServices>(
            SubServices("service1",R.drawable.doctor),
            SubServices("service2",R.drawable.doctor),
            SubServices("service3",R.drawable.doctor),
            SubServices("service4",R.drawable.doctor),
            SubServices("service5",R.drawable.doctor),
            SubServices("service6",R.drawable.doctor),
            SubServices("service7",R.drawable.doctor),
            SubServices("service8",R.drawable.doctor),
            SubServices("service8888888888888 8888888888 888888  888888888888888888",R.drawable.doctor),
            SubServices("service1",R.drawable.doctor),
            SubServices("service2",R.drawable.doctor),
            SubServices("service3",R.drawable.doctor),
            SubServices("service4",R.drawable.doctor),
            SubServices("service5",R.drawable.doctor),
            SubServices("service6",R.drawable.doctor),
            SubServices("service7",R.drawable.doctor),
            SubServices("service8",R.drawable.doctor),
            SubServices("service888888888888888888888888888888888888888888888888888",R.drawable.doctor)
        )
        nurseSubServicesList.addAll(nurseList)
        medicalSubServicesList.addAll(medicalList)
    }
}