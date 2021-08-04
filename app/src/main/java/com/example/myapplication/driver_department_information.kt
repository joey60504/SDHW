package com.example.myapplication

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.myapplication.databinding.ActivityDriverDepartmentInformationBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_driver_department_information.*
import kotlinx.android.synthetic.main.activity_driver_department_information2.*
import kotlinx.android.synthetic.main.activity_registration1.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class driver_department_information : AppCompatActivity() {

    private var _binding: ActivityDriverDepartmentInformationBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        _binding=ActivityDriverDepartmentInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        go.setOnClickListener {
            val Date = findViewById<EditText>(R.id.editTextDate).text.toString()
            val time = findViewById<EditText>(R.id.editTextDate2).text.toString()
            val startpoint = findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
            val endpoint1 = findViewById<EditText>(R.id.editTextTextPersonName4).text.toString()
            val carcard = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
            val peoplelimit= findViewById<EditText>(R.id.editTextTextPersonName5).text.toString()

            if(Date.isNotEmpty() && startpoint.isNotEmpty() && endpoint1.isNotEmpty() && carcard.isNotEmpty() && time.isNotEmpty() && peoplelimit.isNotEmpty()){
                var i = Intent(this, driver_department_information2::class.java)
                var bundle=Bundle()
                bundle.putString("Date_EXTRA", Date)
                bundle.putString("time_EXTRA",time)
                bundle.putString("startpoint_EXTRA", startpoint)
                bundle.putString("endpoint_EXTRA", endpoint1)
                bundle.putString("carcard_EXTRA", carcard)
                bundle.putString("peoplelimit_EXTRA",peoplelimit)

                i.putExtra("Extra",bundle)

                startActivity(i)

            }else {
                Toast.makeText(this, "規則不可為空", Toast.LENGTH_SHORT).show()
            }



        }
//        binding.spinner.adapter = MyAdapter(this, listOf("寵物", "YES", "NO"))


    }

    fun back2(p0: View){
        startActivity(Intent(this,HomeFragment::class.java))
    }
    fun datePicker(v: View) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        DatePickerDialog(v.context, { view, year, month, day ->
            val monthfix= month+1
            val dateTime = "$year/$monthfix/$day"
            editTextDate.setText(dateTime)
        }, year, month, day
        ).show()
    }
    fun timePicker(v:View){
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR]
        val minute = calendar[Calendar.MINUTE]
        TimePickerDialog(v.context, {_, hour, minute ->
            if(minute<10){
                val dateTime= "$hour:0$minute"
                editTextDate2.setText(dateTime)
            }
            else{
                val dateTime= "$hour:$minute"
                editTextDate2.setText(dateTime)
            }
        },hour,minute,true
        ).show()
    }



    class MyAdapter(context: Context, item:List<String>): ArrayAdapter<String>(context,
        android.R.layout.simple_spinner_dropdown_item,item) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
            view.setTextColor(Color.WHITE)
            return view
        }

        override fun isEnabled(position: Int): Boolean {
            return position!=0
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
            //set the color of first item in the drop down list to gray
            if(position == 0) {
                view.setTextColor(Color.GRAY)
            } else {
                //here it is possible to define color for other items by
                //view.setTextColor(Color.RED)
            }
            return view
        }
    }

}





