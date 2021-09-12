package com.example.myapplication.ui.slideshow

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.User
import com.example.myapplication.chatroom1
import com.example.myapplication.databinding.FriendItemBinding
import com.example.myapplication.newmessage
import com.example.myapplication.ui.test.chatAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception


class slideAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<slideAdapter.ViewHolder>() {

    lateinit var dataList:HashMap<*,*>
    lateinit var root : HashMap<*,*>
    private lateinit var binding: FriendItemBinding

    class ViewHolder(val view:FriendItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= FriendItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val userlist=dataList.toList()
        val (_,userphone)=userlist[position]
        userphone as HashMap<*,*>
        holder.view.textView2.text=userphone["name"].toString()

        holder.view.cardview1.setOnClickListener {
            itemListener.onItemClick(holder , position)
            Log.d("thisisbutton11", userlist.toString())
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClick{
        fun onItemClick(holder: ViewHolder, position: Int)
    }

}