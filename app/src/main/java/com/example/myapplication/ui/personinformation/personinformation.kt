package com.example.myapplication.ui.personinformation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myapplication.databinding.FragmentNavTestBinding
import com.example.myapplication.databinding.FragmentPersoninformationBinding
import com.example.myapplication.driver_department_information
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
            textView70.text=age
            textView71.text=gender
            textView72.text=photo
            textView69.text=email
            textView74.text=phone
        }
//資料庫完
        return root
    }

}