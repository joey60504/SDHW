package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.SiteItemBinding


class siteadapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<siteadapter.ViewHolder>() {

    lateinit var roommemberList:ArrayList<String>
    lateinit var profilelist:HashMap<*,*>
    private lateinit var binding: SiteItemBinding
    class ViewHolder(val view:SiteItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= SiteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val roommembersphone=roommemberList[position]
        val member=profilelist[roommembersphone] as HashMap<*,*>
        val name=member["name"].toString()
        val pickupinfo=member["PickupINFO"] as HashMap<*,*>
        val site=pickupinfo["site"].toString()
        val time=pickupinfo["time"].toString()
        holder.view.textViewname.text=name
        holder.view.textView42.text=time
        holder.view.textView43.text=site


    }

    override fun getItemCount(): Int {
        return roommemberList.size
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}