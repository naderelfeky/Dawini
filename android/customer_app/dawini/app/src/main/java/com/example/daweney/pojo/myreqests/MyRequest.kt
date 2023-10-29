package com.example.daweney.pojo.myreqests

import android.os.Parcel
import android.os.Parcelable

class MyRequest(
    val __v: Int,
    val _id: String,
    val address: String,
    val customerId: String,
    val date: String,
    val priceOfService: Int,
    val service: List<MyService>,
    val status: String,
    val typeofservice: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.createTypedArrayList(MyService)!!,
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<MyRequest> {
        override fun createFromParcel(parcel: Parcel): MyRequest {
            return MyRequest(parcel)
        }

        override fun newArray(size: Int): Array<MyRequest?> {
            return arrayOfNulls(size)
        }
    }
}

class MyService(
    val ArabicName: String,
    val EnglishName: String,
    val __v: Int,
    val _id: String,
    val price: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<MyService> {
        override fun createFromParcel(parcel: Parcel): MyService {
            return MyService(parcel)
        }

        override fun newArray(size: Int): Array<MyService?> {
            return arrayOfNulls(size)
        }
    }

}