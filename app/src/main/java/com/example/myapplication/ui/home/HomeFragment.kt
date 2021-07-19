package com.example.myapplication.ui.home

import android.R
import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.dialog_view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//spinner
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val petspinner = binding.pet
        petspinner.adapter = ArrayAdapter(
            this.requireContext(), android.R.layout.simple_spinner_dropdown_item,
            listOf("YES", "NO")
        )
        val childspinner = binding.child
        childspinner.adapter = ArrayAdapter(
            this.requireContext(), android.R.layout.simple_spinner_dropdown_item,
            listOf("YES", "NO")
        )
        val genderspinner = binding.gender
        genderspinner.adapter = ArrayAdapter(
            this.requireContext(), android.R.layout.simple_spinner_dropdown_item,
            listOf("MAN", "WOMAN")
        )
        val smokerspinner = binding.smoke
        smokerspinner.adapter = ArrayAdapter(
            this.requireContext(), android.R.layout.simple_spinner_dropdown_item,
            listOf("YES", "NO")
        )
//spinner完


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.select_dialog_item)
            fun EnterRoom(p0: View) {
                val view = View.inflate(this@MainActivity, R.layout.select_dialog_item, null)
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setView(view)
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                val mToast = Toast.makeText(applicationContext, "access clicked" , Toast.LENGTH_SHORT)
                mToast.show()


            }
        }

    }
}
