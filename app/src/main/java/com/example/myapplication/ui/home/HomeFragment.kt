package com.example.myapplication.ui.home

import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.ProfileActivity
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.driver_department_information
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_registration1.*
import kotlinx.android.synthetic.main.dialog_view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_personinformation.*
import java.util.*
import kotlin.collections.HashMap


class HomeFragment : Fragment() {
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
        return root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


//    class MainActivity : AppCompatActivity() {
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContentView(R.layout.select_dialog_item)
//            fun EnterRoom(p0: View) {
//                val view = View.inflate(this@MainActivity, R.layout.select_dialog_item, null)
//                val builder = AlertDialog.Builder(this@MainActivity)
//                builder.setView(view)
//                val dialog = builder.create()
//                dialog.show()
//                dialog.window?.setBackgroundDrawableResource(R.color.transparent)
//                val mToast = Toast.makeText(applicationContext, "access clicked" , Toast.LENGTH_SHORT)
//                mToast.show()
//            }
//        }
//    }
}
