package com.example.myapplication.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.MyroomItemBinding


class myroomAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<myroomAdapter.ViewHolder>() {
    lateinit var likelist:ArrayList<String>
    lateinit var dataList:ArrayList<String>
    private lateinit var binding: MyroomItemBinding

    class ViewHolder(val view:MyroomItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= MyroomItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.cardview11111.setOnClickListener {
            itemListener.onItemClick(position)
        }
        val myroomList=dataList.toList()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}