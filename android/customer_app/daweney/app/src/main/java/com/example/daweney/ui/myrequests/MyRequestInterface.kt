package com.example.daweney.ui.myrequests

import com.example.daweney.pojo.myreqests.RequestResponseItem

interface MyRequestInterface {
    fun onItemClick(request: RequestResponseItem)
    fun onItemLongClick(request: RequestResponseItem)
}