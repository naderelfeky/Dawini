package com.example.navigationdrawar

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

class AddphotoFragment : Fragment() {
    lateinit var imageView: ImageView
    lateinit var button: Button
    private val pickImage =100
    private var imageUri: Uri? = null


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
        val view = inflater.inflate(R.layout.fragment_addphoto, container, false)

        imageView = view.findViewById(R.id.imageView7)
        button = view.findViewById(R.id.add)
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore .Images.Media.INTERNAL_CONTENT_URI)
            gallery.type="image/*"
            startActivityForResult(gallery, pickImage)
        }

        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

}