package com.example.haksamo.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haksamo.R

data class BasicItem(
    val title: String
)

class BasicAdapter(val itemList: MutableList<BasicItem>) : RecyclerView.Adapter<BasicAdapter.BasicViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }
    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicAdapter.BasicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return BasicViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasicAdapter.BasicViewHolder, position: Int) {
        holder.title.text = itemList[position].title
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    inner class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val title = itemView.findViewById<TextView>(R.id.title)

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }

}