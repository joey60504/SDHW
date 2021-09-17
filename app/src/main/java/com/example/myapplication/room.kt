package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityRoomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_room.*
import java.lang.Exception
import java.util.ArrayList

lateinit var auth: FirebaseAuth
lateinit var textviewlist:List<TextView>
lateinit var imagebtnlist:List<ImageButton>

class room : AppCompatActivity() {
    var data1:String=" "
    lateinit var binding:ActivityRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textviewlist= listOf(binding.textView13,binding.textView15,binding.textView16,binding.textView17,binding.textView18,binding.textView33,binding.textView31)
        imagebtnlist= listOf(binding.imageButton7,binding.imageButton9,binding.imageButton12,binding.imageButton13,binding.imageButton14,binding.imageButton3,binding.imageButton4)
        data1 = intent.getStringExtra("Data").toString()
        dataselect()
        whenenterroom()
        binding.imageButton15.setOnClickListener {
            if(data1 != usersphone){
                Toast.makeText(this, "駕駛才能鎖定房間唷", Toast.LENGTH_LONG).show()
            }
            else {
                try {
                    if(roominfo["nolockorlocked"]=="nolock") {
                        nolock_to_locked()
                    }
                    else{
                        locked_to_nolock()
                    }
                }
                catch(e:Exception){
                    Log.d("test",e.toString())
                }
            }
        }
        binding.leavebtn.setOnClickListener {
            if(data1!=usersphone) {
                AlertDialog.Builder(this).apply {
                    setTitle("提醒")
                    setMessage("確認離開?")
                    setPositiveButton("確定?") { _, _ ->
                        leaveroomcoustomer()
                        startActivity(Intent(this@room, homepage::class.java))
                    }
                    setNegativeButton("取消", null)
                }.show()
            }
            else{
                AlertDialog.Builder(this).apply {
                    setTitle("提醒")
                    setMessage("確定要關閉組隊?")
                    setPositiveButton("確定?") { _, _ ->
                        deleteRoomByDriver()
                        startActivity(Intent(this@room, homepage::class.java))
                    }
                    setNegativeButton("取消", null)
                }.show()
            }
        }
        binding.imageButton5.setOnClickListener {
            startActivity(Intent(this@room,homepage::class.java))
        }
        binding.imageButton16.setOnClickListener {
            var phone = auth.currentUser?.phoneNumber.toString()
            if(data1==phone) {
                Intent(this, mapchoice::class.java).apply {
                    putExtra("Data", data1)
                    startActivity(this)
                }
            }
            else{
                Intent(this, mapchoiceForC::class.java).apply {
                    putExtra("Data", data1)
                    startActivity(this)
                }
            }
        }
        binding.button8.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            if (auth.currentUser?.phoneNumber.toString() == data1) {
                Toast.makeText(this, "駕駛不須準備喔~", Toast.LENGTH_SHORT).show()
            } else {
                database.child("room").child(data1).child("roomINFO").child("membeready")
                    .child(phone)
                    .get().addOnSuccessListener {
                        var ready = it.value as String
                        Log.d("...", ready)
                        if (ready == "notready") {
                            AlertDialog.Builder(this).apply {
                                setTitle("提醒")
                                setMessage("請先確認駕駛載客順序再行準備\n準備後不得取消")
                                setPositiveButton("確定?") { _, _ ->
                                    database.child("room").child(data1).child("roomINFO")
                                        .child("membeready")
                                        .child(phone).setValue("ready")
                                    //startActivity(Intent(this@room, homepage::class.java))
                                }
                                setNegativeButton("取消", null)

                            }.show()
                        } else {
                            Toast.makeText(this, "你已經準備囉", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
    val imagelist= listOf<List<Int>>(
        listOf(R.drawable.sam1,R.drawable.sam2,R.drawable.sam3,R.drawable.sam4),
        listOf(R.drawable.emma1,R.drawable.emma2,R.drawable.emma3))
    lateinit var profilelist:HashMap<*,*>
    lateinit var roomlist:HashMap<*,*>
    lateinit var usersphone:String
    lateinit var user:HashMap<*,*>
    lateinit var roominfo:HashMap<*,*>

    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference

        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    clear()
                    val root = dataSnapshot.value as HashMap<*, *>
                    profilelist = root["profile"] as HashMap<*, *>
                    val driver = profilelist[data1] as HashMap<*, *>
                    val driversname = driver["name"].toString()
                    binding.textView13.text = driversname
                    roomlist = root["room"] as HashMap<*, *>
                    usersphone = auth.currentUser?.phoneNumber.toString()
                    user = profilelist[usersphone] as HashMap<*, *>
                    val roomowner = roomlist[data1] as HashMap<*, *>
                    roominfo = roomowner["roomINFO"] as HashMap<*, *>

                    val roommember = roominfo["roommember"] as ArrayList<String>
                    val peoplelimit=roominfo["peoplelimit"].toString()
                    roommember.add(0,data1)
                    selectarraydata(roommember, profilelist)
                    limitpeople(peoplelimit)
                }catch (e:Exception){

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }
    fun selectarraydata(roommember:ArrayList<String>,profilelist:HashMap<*,*>){
        for (i in roommember.indices) {
            filldata(roommember[i],imagebtnlist[i],textviewlist[i],profilelist)
        }
    }
    fun filldata(roommembersphone:String,imagebtn:ImageButton,textV:TextView,profilelist: HashMap<*,*>){
        val members=profilelist[roommembersphone] as HashMap<*,*>

        textV.text=members["name"].toString()

        val samList=imagelist[0]
        val sam=samList.random()
        val emmaList=imagelist[1]
        val emma=emmaList.random()

        if(members["gender"]=="male") {
            imagebtn.setImageResource(sam)
        }
        else{
            imagebtn.setImageResource(emma)
        }
        //val namename=members["name"] as HashMap<*,*>
        imagebtn.setOnClickListener {
            supportFragmentManager.let{ room_dialog3(sam,emma,members,roommembersphone,data1,usersphone).show(it, "room_dialog3") }
        }
    }
    fun limitpeople(peoplelimit:String){
        val peoplecount=peoplelimit.toInt()
        for(i in peoplecount until 7){
            Log.d("888",i.toString())
            imagebtnlist[i+1].setImageResource(R.drawable.roomnopeople1)
        }
    }

    fun locked_to_nolock(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        binding.imageButton15.setImageResource(R.drawable.lock)
        binding.imageButton9.setBackgroundResource(R.drawable.ovalyb)
        binding.imageButton12.setBackgroundResource(R.drawable.ovalyb)
        binding.imageButton13.setBackgroundResource(R.drawable.ovalyb)
        binding.imageButton14.setBackgroundResource(R.drawable.ovalyb)
        binding.imageButton3.setBackgroundResource(R.drawable.ovalyb)
        binding.imageButton4.setBackgroundResource(R.drawable.ovalyb)
        database.child("room").child(data1).child("roomINFO").child("nolockorlocked")
            .setValue("nolock")
    }
    fun nolock_to_locked(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        binding.imageButton15.setImageResource(R.drawable.ic_locked)
        binding.imageButton9.setBackgroundResource(R.drawable.ovalrb)
        binding.imageButton12.setBackgroundResource(R.drawable.ovalrb)
        binding.imageButton13.setBackgroundResource(R.drawable.ovalrb)
        binding.imageButton14.setBackgroundResource(R.drawable.ovalrb)
        binding.imageButton3.setBackgroundResource(R.drawable.ovalrb)
        binding.imageButton4.setBackgroundResource(R.drawable.ovalrb)
        database.child("room").child(data1).child("roomINFO").child("nolockorlocked")
            .setValue("locked")
    }
    fun whenenterroom(){
        try {
            auth = FirebaseAuth.getInstance()
            var database = FirebaseDatabase.getInstance().reference
            database.child("room").child(data1).child("roomINFO").get().addOnSuccessListener {
                val roominfomation=it.value as HashMap<*,*>
                if (roominfomation["nolockorlocked"] == "locked") {
                    binding.imageButton15.setImageResource(R.drawable.ic_locked)
                    binding.imageButton9.setBackgroundResource(R.drawable.ovalrb)
                    binding.imageButton12.setBackgroundResource(R.drawable.ovalrb)
                    binding.imageButton13.setBackgroundResource(R.drawable.ovalrb)
                    binding.imageButton14.setBackgroundResource(R.drawable.ovalrb)
                    binding.imageButton3.setBackgroundResource(R.drawable.ovalrb)
                    binding.imageButton4.setBackgroundResource(R.drawable.ovalrb)
                    Log.d("777", "789")
                } else {
                    binding.imageButton15.setImageResource(R.drawable.lock)
                    binding.imageButton9.setBackgroundResource(R.drawable.ovalyb)
                    binding.imageButton12.setBackgroundResource(R.drawable.ovalyb)
                    binding.imageButton13.setBackgroundResource(R.drawable.ovalyb)
                    binding.imageButton14.setBackgroundResource(R.drawable.ovalyb)
                    binding.imageButton3.setBackgroundResource(R.drawable.ovalyb)
                    binding.imageButton4.setBackgroundResource(R.drawable.ovalyb)
                    Log.d("777", "456")
                }
            }
        }
        catch(e:Exception) {
            Log.d("777", "123")
        }
    }


    lateinit var userssite:String
    fun leaveroomcoustomer(){
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(phone).get().addOnSuccessListener {
            val userinfoleave=it.value as java.util.HashMap<String,Any>

            val joining =userinfoleave["joining"] as ArrayList<String>
            joining.remove(data1)
            userinfoleave.put("joining",joining)
            database.child("profile").child(phone).updateChildren(userinfoleave)

            val PickupINFO=userinfoleave["PickupINFO"] as HashMap<String,Any>
            val driversphoneinPickupINFO=PickupINFO[data1] as HashMap<*,*>
            userssite=driversphoneinPickupINFO["site"].toString()
            val pickinfoDriversphone=PickupINFO[data1] as HashMap<*,*>
            pickinfoDriversphone.keys.remove("site")
            pickinfoDriversphone.keys.remove("time")
            pickinfoDriversphone.keys.remove("other")
            database.child("profile").child(phone).child("PickupINFO").updateChildren(PickupINFO)
        }.continueWith {
            database.child("room").child(data1).child("roomINFO").get().addOnSuccessListener {
                val roominfoleave=it.value as java.util.HashMap<String,Any>

                val roommember1 =roominfoleave["roommember"] as ArrayList<String>
                val indexofuser=roommember1.indexOf(phone)
                val sitearray = roominfoleave["sitearray"] as ArrayList<String>
                sitearray.removeAt(indexofuser)
                roominfoleave.put("sitearray",sitearray)

                val roommember2 =roominfoleave["roommember"] as ArrayList<String>
                roommember2.remove(phone)
                roominfoleave.put("roommember",roommember2)
                try {
                    val truesitearrayList = roominfoleave["truesitearrayList"] as ArrayList<String>
                    truesitearrayList.remove(userssite)
                    roominfoleave.put("truesitearrayList", truesitearrayList)
                }
                catch (e:Exception){

                }
                database.child("room").child(data1).child("roomINFO")
                        .updateChildren(roominfoleave)
                database.child("room").child(data1).child("roomINFO").child("membeready")
                    .child(phone).removeValue()

            }
        }
    }
    fun deleteRoomByDriver(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        database.child("room").child(data1).child("roomINFO").get().addOnSuccessListener {
            val roominfo=it.value as HashMap<*,*>
            val roommember=roominfo["roommember"] as ArrayList<String>
            for (i in roommember.indices) {
                WhenDriverLeaveDeleteCoustomersINFO(roommember[i])
            }
        }.continueWith {
            database.child("room").get().addOnSuccessListener {
                val room = it.value as java.util.HashMap<String, Any>
                val roomowner = room[data1] as HashMap<*, *>
                roomowner.keys.remove("roomINFO")
                roomowner.keys.remove("roomRULE")
                database.child("room").updateChildren(room)
            }
        }
    }
    fun WhenDriverLeaveDeleteCoustomersINFO(roommembersphone:String) {
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(roommembersphone).get().addOnSuccessListener {
            val userinfoleave = it.value as java.util.HashMap<String, Any>

            val joining = userinfoleave["joining"] as ArrayList<String>
            joining.remove(data1)
            userinfoleave.put("joining", joining)
            database.child("profile").child(roommembersphone).updateChildren(userinfoleave)

            val PickupINFO = userinfoleave["PickupINFO"] as HashMap<String, Any>
            val driversphoneinPickupINFO = PickupINFO[data1] as HashMap<*, *>
            userssite = driversphoneinPickupINFO["site"].toString()
            val pickinfoDriversphone = PickupINFO[data1] as HashMap<*, *>
            pickinfoDriversphone.keys.remove("site")
            pickinfoDriversphone.keys.remove("time")
            pickinfoDriversphone.keys.remove("other")
            database.child("profile").child(roommembersphone).child("PickupINFO")
                .updateChildren(PickupINFO)
        }
    }
    fun clear(){
        imagebtnlist.onEach {
            it.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setOnClickListener {
            }
        }
        textviewlist.onEach {
            it.text=""
        }
    }
}

