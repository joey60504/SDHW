package com.example.myapplication.ui.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNavTestBinding
import com.example.myapplication.databinding.FragmentSlideshowBinding
import com.example.myapplication.ui.slideshow.SlideshowViewModel


private var _binding: FragmentNavTestBinding?=null
private val binding get() = _binding!!

class nav_test : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavTestBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}