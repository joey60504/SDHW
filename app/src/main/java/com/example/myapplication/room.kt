package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import com.example.myapplication.databinding.ActivityRoomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

lateinit var auth: FirebaseAuth
lateinit var data1:String
lateinit var textviewlist:List<TextView>
lateinit var imagebtnlist:List<ImageButton>
class room : AppCompatActivity() {
    lateinit var binding:ActivityRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textviewlist= listOf(binding.textView15,binding.textView16,binding.textView17,binding.textView18,binding.textView33,binding.textView31)
        imagebtnlist= listOf(binding.imageButton9,binding.imageButton12,binding.imageButton13,binding.imageButton14,binding.imageButton3,binding.imageButton4)
        data1 = intent.getStringExtra("Data").toString()
        dataselect()
    }
    val imagelist= listOf<List<Int>>(
        listOf(R.drawable.sam1,R.drawable.sam2,R.drawable.sam3,R.drawable.sam4),
        listOf(R.drawable.emma1,R.drawable.emma2,R.drawable.emma3))
    lateinit var profilelist:HashMap<*,*>
    lateinit var roomlist:HashMap<*,*>
    lateinit var usersphone:String
    lateinit var user:HashMap<*,*>
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                profilelist=root["profile"] as HashMap<*,*>
                roomlist=root["room"] as HashMap<*,*>
                usersphone =auth.currentUser?.phoneNumber.toString()
                user=profilelist[usersphone] as HashMap<*,*>
                val roomowner=roomlist[data1] as HashMap<*,*>
                val roominfo=roomowner["roomINFO"] as HashMap<*,*>
                val roommember=roominfo["roommember"] as ArrayList<String>
                selectarraydata(roommember,profilelist)

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addValueEventListener(dataListener)
    }
    fun selectarraydata(roommember:ArrayList<String>,profilelist:HashMap<*,*>){
        for (i in roommember.indices) {
            Log.d("test",roommember[i])
            filldata(roommember[i],imagebtnlist[i],textviewlist[i],profilelist)
        }

    }
    fun filldata(roommembersphone:String,imagebtn:ImageButton,textV:TextView,profilelist: HashMap<*,*>){
        val members=profilelist[roommembersphone] as HashMap<*,*>
        textV.text=members["name"].toString()
        if(members["gender"]=="male") {
            val samList=imagelist[0]
            imagebtn.setImageResource(samList.random())
        }
        else{
            val emmaList=imagelist[1]
            imagebtn.setImageResource(emmaList.random())
        }
    }








    fun entergooglemap(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root = dataSnapshot.value as HashMap<*, *>
                val room = root["room"] as HashMap<*, *>
                val roomowner=room[data1] as HashMap<*,*>
                val roominfo = roomowner["roomINFO"] as HashMap<*, *>
                val ownerstartpoint = roominfo["startpoint"].toString()
                val ownerendpoint = roominfo["endpoint1"].toString()
                val url = Uri.parse(
                    "https://www.google.com/maps/dir/?api=1&origin=" + ownerstartpoint + "&destination=" + ownerendpoint + "&travelmode=driving"
                )
                val intent = Intent().apply {
                    action = "android.intent.action.VIEW"
                    data = url
                }
                startActivity(intent)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }
}