package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.RoomDialog3Binding
import com.example.myapplication.newmessage.Companion.USER_KEY
import com.example.myapplication.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.util.ArrayList

class room_dialog3(val pctmale:Int,val pctfemale:Int,val members:HashMap<*,*>,val roomp:String,val driversphone:String,val myphone:String): DialogFragment() {
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
                binding.imageView8.setImageResource(pctmale)
            } else {
                binding.imageView8.setImageResource(pctfemale)
            }
        }

        else {
            binding.imageButton.setImageResource(R.drawable.ic_cancelw)
            binding.TVusername.text = members["name"].toString()
            try {
                val pickupinfo = members["PickupINFO"] as HashMap<*, *>
                val pickupinfodriversphone = pickupinfo[driversphone] as HashMap<*, *>
                val time = pickupinfodriversphone["time"].toString()
                val other = pickupinfodriversphone["other"].toString()
                binding.textView51.text = "希望上車時間 :$time"
                binding.textView52.text = "備註 :$other"
            }
            catch(e:Exception){}
            if (members["gender"] == "male") {
                binding.imageView8.setImageResource(pctmale)
            } else {
                binding.imageView8.setImageResource(pctfemale)
            }
        }

        binding.imageButton.setOnClickListener{
            if(driversphone==myphone){
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("提醒")
                    setMessage("確認踢出此乘客?")
                    setPositiveButton("確定?") { _, _ ->
                        kickcoustomer()
                        dismiss()
                    }
                    setNegativeButton("取消", null)
                }.show()
            }
            else{
                Toast.makeText(requireContext(), "只有駕駛可以刪除乘客唷", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imageButton6.setOnClickListener {
            addfriend()
        }
        return binding.root
    }


    lateinit var userssite:String
    fun kickcoustomer(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(roomp).get().addOnSuccessListener {
            val userinfoleave=it.value as java.util.HashMap<String,Any>

            val joining =userinfoleave["joining"] as ArrayList<String>
            joining.remove(driversphone)
            userinfoleave.put("joining",joining)
            database.child("profile").child(roomp).updateChildren(userinfoleave)

            val PickupINFO=userinfoleave["PickupINFO"] as HashMap<String,Any>
            val driversphoneinPickupINFO=PickupINFO[driversphone] as HashMap<*,*>
            userssite=driversphoneinPickupINFO["site"].toString()
            val pickinfoDriversphone=PickupINFO[driversphone] as HashMap<*,*>
            pickinfoDriversphone.keys.remove("site")
            pickinfoDriversphone.keys.remove("time")
            pickinfoDriversphone.keys.remove("other")
            database.child("profile").child(roomp).child("PickupINFO").updateChildren(PickupINFO)
        }.continueWith {
            database.child("room").child(driversphone).child("roomINFO").get().addOnSuccessListener {
                val roominfoleave=it.value as java.util.HashMap<String,Any>

                val roommember1 =roominfoleave["roommember"] as ArrayList<String>
                val indexofuser=roommember1.indexOf(roomp)
                val sitearray = roominfoleave["sitearray"] as ArrayList<String>
                sitearray.removeAt(indexofuser)
                roominfoleave.put("sitearray",sitearray)

                val roommember2 =roominfoleave["roommember"] as ArrayList<String>
                roommember2.remove(roomp)
                roominfoleave.put("roommember",roommember2)

                val truesitearrayList = roominfoleave["truesitearrayList"] as ArrayList<String>
                truesitearrayList.remove(userssite)
                roominfoleave.put("truesitearrayList",truesitearrayList)
                database.child("room").child(driversphone).child("roomINFO").updateChildren(roominfoleave)

            }
        }
    }
    fun addfriend(){
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(phone).get().addOnSuccessListener {
            val user=it.value as java.util.HashMap<String,Any>
            if(user["friendlist"]!=null){
                val friendlist =user["friendlist"] as ArrayList<String>
                if(roomp in friendlist) {
                    Toast.makeText(requireContext(), "你們已經是朋友囉!", Toast.LENGTH_SHORT).show()
                }
                else{
                    friendlist.add(roomp)
                    user.put("friendlist", friendlist)
                    database.child("profile").child(phone).updateChildren(user)
                }
            }
            else{
                user.put("friendlist", arrayListOf<String>(roomp))
                database.child("profile").child(phone).updateChildren(user)
            }
        }
        database.child("profile").child(roomp).get().addOnSuccessListener {
            val user=it.value as java.util.HashMap<String,Any>
            if(user["friendlist"]!=null){
                val friendlist =user["friendlist"] as ArrayList<String>
                if(phone in friendlist) {
                    Log.d("test7","33")
                    Toast.makeText(requireContext(), "你們已經是朋友囉!", Toast.LENGTH_SHORT).show()
                }
                else{
                    Log.d("test7","22")
                    friendlist.add(phone)
                    user.put("friendlist", friendlist)
                    database.child("profile").child(roomp).updateChildren(user)
                }
            }
            else{
                Log.d("test7","44")
                user.put("friendlist", arrayListOf<String>(phone))
                database.child("profile").child(roomp).updateChildren(user)
            }
        }
    }


}
