package com.example.daweney.ui.sevices

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.daweney.R
import com.example.daweney.pojo.myreqests.RequestResponseItem
import com.example.daweney.pojo.services.ServicesResponse
import com.example.daweney.pojo.services.ServicesResponseItem
import java.util.*
import kotlin.collections.ArrayList


class ServicesAdapter(val services: ServicesResponse) :
    RecyclerView.Adapter<ServicesAdapter.RequestViewHolder>(), Filterable {
    private val fullServicesList=ArrayList<ServicesResponseItem>()

    init{
        fullServicesList.addAll(services)
    }

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView =itemView.findViewById(R.id.subServices_img)
        val textView: TextView =itemView.findViewById(R.id.TV_SubServices_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.services_list_row, parent, false)
        return ServicesAdapter.RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val service=services[position]
        holder.textView.text=service.EnglishName
//        holder.imageView.setImageResource(R.drawable.ic_add)
    }

    override fun getItemCount(): Int {
        return services.size
    }

    override fun getFilter(): Filter {
        return servicesFiler
    }
    private val servicesFiler = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<ServicesResponseItem>()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(fullServicesList)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()
                for (item in fullServicesList) {

                    if (item.EnglishName.lowercase(Locale.ROOT).contains(filterPattern)||
                        item.ArabicName.lowercase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            services.clear()
            services.addAll(results?.values as List<ServicesResponseItem>)
            notifyDataSetChanged()
        }
    }
}