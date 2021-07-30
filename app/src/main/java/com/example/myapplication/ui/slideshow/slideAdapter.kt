package com.example.myapplication.ui.slideshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FriendItemBinding


class slideAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<slideAdapter.ViewHolder>() {

    lateinit var dataList:ArrayList<String>
    private lateinit var binding: FriendItemBinding

    class ViewHolder(val view:FriendItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= FriendItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textView2.text=dataList[position]
        holder.view.cardview1.setOnClickListener {
            itemListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}