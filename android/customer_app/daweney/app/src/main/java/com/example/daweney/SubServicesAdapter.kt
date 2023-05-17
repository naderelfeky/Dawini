package com.example.daweney

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.subservices_list_row.view.*
import java.util.zip.Inflater

class SubServicesAdapter(context:Context,subServices: ArrayList<SubServices>): ArrayAdapter<SubServices>(context,0,subServices) {


  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

    var list:View?=convertView
    if(convertView==null){
      list=LayoutInflater.from(context).inflate(R.layout.subservices_list_row,parent,false)
    }
    val item =getItem(position)

      list?.TV_SubServices_name?.text=item?.name
      list?.subServices_img?.setImageResource(item?.img?:R.drawable.doctor)


    return list!!
  }
}