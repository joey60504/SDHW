package com.example.myapplication.ui.personinformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentNavTestBinding
import com.example.myapplication.databinding.FragmentPersoninformationBinding
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
        var getdata=object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    var name=i.child(phone).child("name").getValue()
                    var age=i.child(phone).child("age").getValue()
                    var gender=i.child(phone).child("gender").getValue()
                    var photo=i.child(phone).child("photo").getValue()
                    var email=i.child(phone).child("email").getValue()
                    textView64.text=name.toString()
                    textView70.text=age.toString()
                    textView71.text=gender.toString()
                    textView72.text=photo.toString()
                    textView69.text=email.toString()
                    textView74.text=phone
                }
            }
        }
        database.addValueEventListener(getdata)
//資料庫完
        return root
    }

}