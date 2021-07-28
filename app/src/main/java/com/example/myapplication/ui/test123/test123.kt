package com.example.myapplication.ui.test123

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTest123Binding
import com.example.myapplication.homepage


private var _binding: FragmentTest123Binding?=null
private val binding get() = _binding!!

class test123 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTest123Binding.inflate(inflater, container, false)
        val root: View = binding.root

        binding!!.backLikelist.setOnClickListener{
            startActivity(Intent(requireContext(), homepage::class.java))
        }
        return root


    }

}

