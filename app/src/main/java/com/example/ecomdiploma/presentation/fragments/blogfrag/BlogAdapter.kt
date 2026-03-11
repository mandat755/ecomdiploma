package com.example.ecomdiploma.presentation.fragments.blogfrag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomdiploma.R

class BlogAdapter(private val blogList: List<BlogItem>) : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blogItem = blogList[position]
        holder.imageView.setImageResource(blogItem.imageResId)
        holder.text1.text = blogItem.text1
        holder.text2.text = blogItem.text2
        holder.text3.text = blogItem.text3
        holder.text4.text = blogItem.text4
    }

    override fun getItemCount(): Int = blogList.size

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.cardImage)
        val text1: TextView = itemView.findViewById(R.id.text1)
        val text2: TextView = itemView.findViewById(R.id.text2)
        val text3: TextView = itemView.findViewById(R.id.text3)
        val text4: TextView = itemView.findViewById(R.id.text4)
    }
}