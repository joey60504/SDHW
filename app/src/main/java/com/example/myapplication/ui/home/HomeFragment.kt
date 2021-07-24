package com.example.myapplication.ui.home

import android.R
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.myapplication.ProfileActivity
import com.example.myapplication.databinding.DialogViewBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.driver_department_information
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_registration1.*
import kotlinx.android.synthetic.main.dialog_view.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_personinformation.*
import java.util.*
import kotlin.collections.HashMap


class HomeFragment : Fragment()  {
    lateinit var auth: FirebaseAuth
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
//無駕照無法點進
        _binding!!.addhouse.setOnClickListener(){
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            database.child("profile").child(phone).get().addOnSuccessListener {
                val user =it.value as HashMap<*,*>
                var photo=user["photo"].toString()
                if(photo=="NO"){
                    Toast.makeText(requireContext(),"無駕照者無法開團", Toast.LENGTH_LONG).show()
                }
                else{
                    startActivity(Intent(requireContext(),driver_department_information::class.java))
                }

            }
        }
//無駕照無法點進完
        _binding!!.housebutton.setOnClickListener{
            val cusDialog=CusDialog()
            activity?.supportFragmentManager?.let { it1 -> cusDialog.show(it1,"cusdialog") }
        }


        return root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    class CusDialog:DialogFragment(){
//        View元素綁定
        private lateinit var binding: DialogViewBinding
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

//            binding實例化
            binding= DialogViewBinding.inflate(layoutInflater)

//            關閉按鈕
            binding.close.setOnClickListener {
                dismiss()
            }

//            進入按鈕
            binding.access.setOnClickListener {
                Log.d("dialog","hiiiiii")
            }

//            TODO 實作
            return binding.root

        }
    }




}
