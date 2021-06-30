package com.example.myapplication.ui.home

import android.R
import android.icu.number.Notation.simple
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentHomeBinding
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test1.*

class HomeFragment : Fragment()  {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val petspinner=binding.pet
        petspinner.adapter=ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_dropdown_item,
            listOf("YES","NO"))
        val childspinner=binding.child
        childspinner.adapter=ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_dropdown_item,
            listOf("YES","NO"))
        val genderspinner=binding.gender
        genderspinner.adapter=ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_dropdown_item,
            listOf("MAN","WOMAN"))
        val smokerspinner=binding.smoke
        smokerspinner.adapter=ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_dropdown_item,
            listOf("YES","NO"))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}