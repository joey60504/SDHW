package com.example.myapplication.ui.TGOSMDFK

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTGOSMDFKBinding

class TGOSMDFK : Fragment() {
    private var _binding: FragmentTGOSMDFKBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentTGOSMDFKBinding.inflate(inflater,container,false)
        val root:View= binding.root

        binding.webview.apply{
            loadUrl("https://tgossd.site/")
        }

        return root
    }
}