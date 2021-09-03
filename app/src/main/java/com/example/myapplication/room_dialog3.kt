package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.RoomDialog3Binding
import com.example.myapplication.newmessage.Companion.USER_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class room_dialog3(val pctmale:Int,val pctfemale:Int,val members:HashMap<*,*>,val roomp:String,val driversphone:String): DialogFragment() {
    var chatuser: User? = null

    lateinit var auth: FirebaseAuth
    private lateinit var binding: RoomDialog3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RoomDialog3Binding.inflate(layoutInflater)
        val ref = FirebaseDatabase.getInstance().getReference("/profile/$roomp")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                //val ddd =members.getValue(User::class.java)
                chatuser =p0.getValue(User::class.java)

                binding.imageButton2.setOnClickListener {
                    Log.d("thisisbutton", chatuser?.name.toString())

                    val intent = Intent(context,chatroom1::class.java)

                    intent.putExtra(USER_KEY, chatuser)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        if(roomp==driversphone){
            binding.TVusername.text=members["name"].toString()
            if (members["gender"] == "male") {
                binding.imageView5.setImageResource(pctmale)
            } else {
                binding.imageView5.setImageResource(pctfemale)
            }
        }
        else {
            binding.TVusername.text = members["name"].toString()
            try {
                val pickupinfo = members["PickupINFO"] as HashMap<*, *>
                val pickupinfodriversphone = pickupinfo[driversphone] as HashMap<*, *>
                val time = pickupinfodriversphone["time"].toString()
                val other = pickupinfodriversphone["other"].toString()
                binding.textView45.text = "希望上車時間 :$time"
                binding.textView46.text = "備註 :$other"
            }
            catch(e:Exception){}
            if (members["gender"] == "male") {
                binding.imageView5.setImageResource(pctmale)
            } else {
                binding.imageView5.setImageResource(pctfemale)
            }
        }

        return binding.root


    }
}
