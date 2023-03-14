package com.example.navigationdrawar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class AccountFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_account, container, false)

        val btn1 : ImageView = view.findViewById(R.id.imageView3)
        btn1.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,AddphotoFragment())?.commit()
        }

        val btn2 : ImageView = view.findViewById(R.id.imageView)
        btn2.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,ChangenameFragment())?.commit()
        }

        val btn3 : ImageView = view.findViewById(R.id.imageView4)
        btn3.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,ChangephoneFragment())?.commit()
        }

        val btn4 : ImageView = view.findViewById(R.id.imageView5)
        btn4.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,ChangehomeFragment())?.commit()
        }

        return view
    }


}