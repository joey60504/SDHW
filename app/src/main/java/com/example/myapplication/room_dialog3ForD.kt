package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.RoomDialog3Binding
import com.google.firebase.auth.FirebaseAuth

class room_dialog3ForD(val pct:Int,val name:String): DialogFragment() {
    lateinit var auth: FirebaseAuth
    private lateinit var binding: RoomDialog3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= RoomDialog3Binding.inflate(layoutInflater)

        binding.imageView5.setImageResource(pct)
        binding.TVusername.text=name

        return binding.root


    }
}
