package com.example.daweney.ui.myrequests

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.daweney.R
import com.example.daweney.pojo.myreqests.RequestResponse
import com.example.daweney.pojo.myreqests.RequestResponseItem
import com.example.daweney.pojo.myreqests.Service
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RequestRecyclerViewAdapter (private val requests: RequestResponse,private val myRequestInterface: MyRequestInterface):
    RecyclerView.Adapter<RequestRecyclerViewAdapter.RequestViewHolder>() ,Filterable{
    private val fullRequestsList=ArrayList<RequestResponseItem>()
    init {
        fullRequestsList.addAll(requests)
    }

    class RequestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val requestBackground: FrameLayout =itemView.findViewById(R.id.requestBackground)
        val serviceType: TextView =itemView.findViewById(R.id.serviceType)
        val serviceTypeImg: ImageView =itemView.findViewById(R.id.serviceTypeImg)
        val requestTime: TextView =itemView.findViewById(R.id.requestTime)
        val requestDate: TextView =itemView.findViewById(R.id.requestDate)
        val requestServices: TextView =itemView.findViewById(R.id.requestServices)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.request_item,parent,false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request=requests[position]
        holder.requestBackground.setBackgroundResource(requestsStatusBackground(request.status))
        holder.serviceType.text = request.typeofservice
        holder.requestDate.text=getDate(request.date)
        holder.requestTime.text=getTime(request.date)
        holder.requestServices.text=getServices(request.service)
        holder.serviceTypeImg.setImageResource(getServiceImg(request.typeofservice))

        holder.itemView.setOnClickListener {
            myRequestInterface.onItemClick(request)
        }
       holder.itemView.setOnLongClickListener {
           myRequestInterface.onItemLongClick(request)
           true
       }
    }

    private fun requestsStatusBackground(status: String): Int {
        return when(status){
            "pending" ->{R.drawable.linear_red_orange_color}
            "applied" ->{R.color.light_blue_200}
            else     ->{R.drawable.linear_sky_green_color}
        }
    }


    private fun getServiceImg(typeOfService: String): Int {
        return when(typeOfService){
            "nursing"-> {R.drawable.doctor}
            "radio"-> {R.drawable.doctor}
            "doctor"-> {R.drawable.doctor}
            else-> {R.drawable.doctor}
        }
    }


    private fun getServices(service: List<Service>): String {
        val currentLocale: Locale = Locale.getDefault()
        val language: String = currentLocale.language
        var serviceList: String = ""
        if (language == "ar") {
            for (s in service) serviceList += s.ArabicName+"\n"
        }else{
            for (s in service) serviceList += s.EnglishName+"\n"
        }
        return serviceList
    }

    private fun getDate(dateString: String): String {

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date: Date = format.parse(dateString) as Date

        val dateFormat = SimpleDateFormat("yy/MMM/dd", Locale.getDefault())

        return dateFormat.format(date)
    }

    private fun getTime(dateString: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date: Date = format.parse(dateString) as Date
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return timeFormat.format(date)
    }


    override fun getItemCount(): Int {
        return requests.size
    }

    override fun getFilter(): Filter {
        return requestFilter
    }
    private val requestFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<RequestResponseItem>()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(fullRequestsList)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (item in fullRequestsList) {
                    if (item.service.toString().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }


        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            requests.clear()
            requests.addAll(results.values as List<RequestResponseItem>)
            notifyDataSetChanged()
        }
    }

}