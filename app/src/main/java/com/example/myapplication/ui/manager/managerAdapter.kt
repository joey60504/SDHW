package com.example.myapplication.ui.manager

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ManagerItemBinding

class managerAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<managerAdapter.ViewHolder>() {

    lateinit var dataList:HashMap<*,*>
    lateinit var profilelist:ArrayList<String>
    private lateinit var binding: ManagerItemBinding

    class ViewHolder(val view: ManagerItemBinding   ): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ManagerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phone=profilelist[position]
        val user=dataList[phone] as HashMap<*,*>
        val name=user["name"].toString()
        holder.view.textView62.text=name


        if(user["goodlist"]!=null) {
            val good = user["goodlist"]
            good as ArrayList<String>
            holder.view.textView69.text=good.size.toString()
        }
        else {
            holder.view.textView69.text = "0"
        }

        if(user["badlist"]!=null) {
            val bad = user["badlist"]
            bad as ArrayList<String>
            if(bad.size>=3){
                holder.view.textView70.text = bad.size.toString()
                holder.view.textView70.setTextColor(Color.parseColor("#FF0000"))
            }
            else {
                holder.view.textView70.text = bad.size.toString()
            }
        }
        else {
            holder.view.textView70.text = "0"
        }


        holder.view.managercardview.setOnClickListener {
            itemListener.onItemClick(holder,position)
        }
    }

    override fun getItemCount(): Int {
        return profilelist.size
    }

    interface OnItemClick{
        fun onItemClick(holder: ViewHolder, position: Int)
    }

}