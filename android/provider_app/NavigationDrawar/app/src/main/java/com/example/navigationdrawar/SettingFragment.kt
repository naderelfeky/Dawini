package com.example.navigationdrawar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment() {


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
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        val btn : ImageView = view.findViewById(R.id.imageView3)
        btn.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,ChthemeFragment())?.commit()
        }

        val btn2 : ImageView = view.findViewById(R.id.imageView)
        btn2.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,ChpasswordFragment())?.commit()
        }

        val btn3 : ImageView = view.findViewById(R.id.imageView4)
        btn3.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,AboutusFragment())?.commit()
        }
        return view
        }

    }


