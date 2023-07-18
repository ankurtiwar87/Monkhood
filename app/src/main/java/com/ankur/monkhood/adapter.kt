package com.ankur.monkhood

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ankur.monkhood.Room.UsersEntity
import com.ankur.monkhood.databinding.ItemBinding
import com.bumptech.glide.Glide

class adapter(private val context: Context,private val list:ArrayList<UsersEntity>):RecyclerView.Adapter<adapter.ViewHolder>() {


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val binding = ItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        Glide.with(context).load(currentItem.img).into(holder.binding.imageView2)
        holder.binding.textView2.text=currentItem.Name
        holder.binding.textView3.text=currentItem.Phone
        holder.binding.textView4.text=currentItem.email
        holder.binding.textView5.text=currentItem.date

    }
}