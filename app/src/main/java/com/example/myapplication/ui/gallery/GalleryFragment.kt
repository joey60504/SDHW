package com.example.myapplication.ui.gallery

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGalleryBinding
import com.example.myapplication.homepage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

lateinit var auth: FirebaseAuth
private var _binding: FragmentGalleryBinding?=null
private val binding get() = _binding!!
class GalleryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding!!.backMyroom.setOnClickListener{
            startActivity(Intent(requireContext(), homepage::class.java))
        }


        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("room").child(phone).get().addOnSuccessListener {
            val roominfo = it.value as java.util.HashMap<String, Any>
            val data=roominfo["roomINFO"] as HashMap<*,*>
            val time=data["time"].toString()
            binding.textView80.text=time


        }

        return root
    }
}