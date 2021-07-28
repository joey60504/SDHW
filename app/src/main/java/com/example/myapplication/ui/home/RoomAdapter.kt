package com.example.myapplication.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.RoomItemBinding


class RoomAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    lateinit var dataList:ArrayList<String>
    private lateinit var binding: RoomItemBinding

    class ViewHolder(val view:RoomItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= RoomItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textView3.text=dataList[position]
        holder.view.cardView.setOnClickListener {
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