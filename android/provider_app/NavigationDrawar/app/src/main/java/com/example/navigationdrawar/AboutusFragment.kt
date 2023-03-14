package com.example.navigationdrawar

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class AboutusFragment : Fragment() {


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
        val view = inflater.inflate(R.layout.fragment_aboutus, container, false)

        val textView: TextView = view.findViewById(R.id.textView10)
        val text: String =  "Our Story:\n "+"The idea behind Our App started when a woman diagnosed with cancer who could not move urgently needed a doctor at home. At the end, the doctor came very late after the condition declined severely, here we understood understand how time affects patients and their families lives. We know very well that we face some difficulties in receiving medical care, which has made some patients neglect their health to avoid waiting in clinics for long hours or searching for a place in a hospital with thousands of money.\n"+
                " About us:\n"+"As we keep our patients ahead, we took a positive attitude to change Egypt's home medical services by developing a complete platform that includes all required medical services to make patients' lives easier. We have been inspired by the international healthcare system challenges and how patient accessibility to healthcare facilities became a problem, which ultimately created a demand for the home-based healthcare system to help saving patients' lives. Technology and smart devices development enabled us to create a user-friendly platform. Also, how the customers are connected to their phones and using them as the first way to meet their needs or communicate with a person, illuminated our vision to combine the medical services with application patients can depend on.\n"+
                " Mission:\n"+"Passionately helping patients to have their best personal home healthcare experience by partnering with professional healthcare providers to guarantee together the highest service quality according to international guidelines.\n" +
                "Vision:\n"+"Re-shaping the future of home healthcare services by providing patient tailored healthcare solutions empowering people to restore the quality of life.\n"+
                " Values:\n"+"We will accomplish our mission and achieve our vision by following our values\n"+"Compassion:\n"+"We sympathize with the patient and his family problems and needs.\n"+
                "Honesty:\n"+"We ensure that all patients and medical service providers medical information or personal information is correct.\n"+
                "Responsibility:\n"+"We are accountable to users from the beginning of their choice of our services until the completion of the process successfully. \n"+
                "Reliability:\n"+"the patient can be assured, he will recieve the same quality service every time he uses our services .\n"
        textView.text = text
        textView.movementMethod = ScrollingMovementMethod()


        val btn : TextView= view.findViewById(R.id.buttonok)
        btn.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,SettingFragment())?.commit()
        }

        return view
    }

}