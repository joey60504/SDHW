package com.example.myapplication.ui.personinformation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentPersoninformationBinding
import com.example.myapplication.homepage
import com.example.myapplication.modifypersoninformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_personinformation.*


private var _binding: FragmentPersoninformationBinding?=null
private val binding get() = _binding!!

class personinformation : Fragment() {
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersoninformationBinding.inflate(inflater, container, false)
        val root: View = binding.root
//資料庫
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(phone).get().addOnSuccessListener {
            val user =it.value as HashMap<*,*>
            var name=user["name"].toString()
            var age=user["age"].toString()
            var gender=user["gender"].toString()
            var photo=user["photo"].toString()
            var email=user["email"].toString()
            textView64.text=name
            textView35.text=age
            textView36.text=gender
            textView38.text=photo
            textView41.text=email
            textview41.text=phone
        }
        _binding!!.imageButton51.setOnClickListener {
            startActivity(Intent(requireContext(),modifypersoninformation::class.java))
        }
        _binding!!.backPersoninformation.setOnClickListener{
            startActivity(Intent(requireContext(),homepage::class.java))
        }

//資料庫完
        return root
    }

}