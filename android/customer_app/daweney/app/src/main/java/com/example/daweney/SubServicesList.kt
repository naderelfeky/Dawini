package com.example.daweney
// to use single_ton
object SubServicesList {
    var nurseSubServicesList= arrayListOf<SubServices>()
    var medicalSubServicesList= arrayListOf<SubServices>( )
    init {
        val  nurseList= arrayListOf<SubServices>(
            SubServices("nurse_subService1",R.drawable.doctor),
            SubServices("nurse_subService2",R.drawable.doctor),
            SubServices("nurse_subService3",R.drawable.doctor),
            SubServices("nurse sub  Service nurse  nurse sub  Service nurse sub nurse sub  Service nurse sub  Service  nurse sub Service4",R.drawable.doctor),
            SubServices("nurse_subService5",R.drawable.doctor),
            SubServices("nurse_subService6",R.drawable.doctor),
            SubServices("nurse_subService7",R.drawable.doctor),
            SubServices("nurse_subService8",R.drawable.doctor),
            SubServices("nurse_subService9",R.drawable.doctor),
            SubServices("nurse_subService10",R.drawable.doctor),
            SubServices("nurse_subService11",R.drawable.doctor),
            SubServices("nurse_subService12",R.drawable.doctor),
            SubServices("nurse_subService13",R.drawable.doctor),
            SubServices("nurse_subService14",R.drawable.doctor),
            SubServices("nurse_subService15",R.drawable.doctor),
        )
        val medicalList= arrayListOf<SubServices>(
            SubServices("medical_subService1",R.drawable.doctor),
            SubServices("medical_subService2",R.drawable.doctor),
            SubServices("medical_subService3 ",R.drawable.doctor),
            SubServices("medical_subService4 ",R.drawable.doctor),
            SubServices("medical_subService5 ",R.drawable.doctor),
            SubServices("medical_subService6 ",R.drawable.doctor),
            SubServices("medical_subService7 ",R.drawable.doctor),
            SubServices("medical  sub Service 8 medical_subService  medical sub Service ",R.drawable.doctor),
            SubServices("medical_subService9 ",R.drawable.doctor),
            SubServices("medical_subService10 ",R.drawable.doctor),
            SubServices("medical_subService11 ",R.drawable.doctor),
            SubServices("medical_subService12 ",R.drawable.doctor),
        )
        nurseSubServicesList.addAll(nurseList)
        medicalSubServicesList.addAll(medicalList)
    }
}