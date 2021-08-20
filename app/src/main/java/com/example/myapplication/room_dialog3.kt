package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.RoomDialog3Binding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.room_dialog3.*

class room_dialog3(val pctmale:Int,val pctfemale:Int,val members:HashMap<*,*>): DialogFragment() {
    lateinit var auth: FirebaseAuth
    private lateinit var binding: RoomDialog3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= RoomDialog3Binding.inflate(layoutInflater)

        binding.TVusername.text=members["name"].toString()
        if(members["gender"]=="male") {
            binding.imageView5.setImageResource(pctmale)
        }
        else{
            binding.imageView5.setImageResource(pctfemale)
        }

        return binding.root
    }
}