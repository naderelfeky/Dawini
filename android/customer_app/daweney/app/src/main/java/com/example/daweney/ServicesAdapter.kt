package com.example.daweney
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.services_list_row.view.*

class ServicesAdapter(private val context: Context):BaseAdapter() {
    override fun getCount(): Int {
        return ServicesList.servicesList?.size!!
    }

    override fun getItem(position: Int): Any {
        return ServicesList.servicesList?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?=convertView
        if(convertView==null){
            view=LayoutInflater.from(context).inflate(R.layout.services_list_row,parent,false)
        }
        val item:Services= getItem(position) as Services
        view?.TV_services?.text=item.name
        view?.services_img?.setImageResource(item.img)

        return view!!
    }

}