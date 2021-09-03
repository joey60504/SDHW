package com.example.myapplication.ui.TGOS

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentTGOSBinding

class TGOS : Fragment() {
    private var _binding: FragmentTGOSBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentTGOSBinding.inflate(inflater,container,false)
        val root:View= binding.root

        binding.webview.apply{
            settings.javaScriptEnabled=true
            loadUrl("https://tgossd.site/map")
        }

        return root
    }
}