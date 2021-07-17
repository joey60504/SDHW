package com.example.myapplication.ui.personinformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentNavTestBinding
import com.example.myapplication.databinding.FragmentPersoninformationBinding


private var _binding: FragmentPersoninformationBinding?=null
private val binding get() = _binding!!

class personinformation : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersoninformationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}