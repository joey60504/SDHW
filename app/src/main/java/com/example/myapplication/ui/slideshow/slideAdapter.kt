package com.example.myapplication.ui.slideshow

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FriendItemBinding
import java.lang.Exception


class slideAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<slideAdapter.ViewHolder>() {

    lateinit var dataList:HashMap<*,*>
    private lateinit var binding: FriendItemBinding

    class ViewHolder(val view:FriendItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= FriendItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.cardview1.setOnClickListener {
            itemListener.onItemClick(position)
        }
        val userlist=dataList.toList()
        val (_,userphone)=userlist[position]
        userphone as HashMap<*,*>
        holder.view.textView2.text=userphone["name"].toString()



    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }

}