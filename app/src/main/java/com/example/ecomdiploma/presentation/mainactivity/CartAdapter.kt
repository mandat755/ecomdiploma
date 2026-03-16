package com.example.ecomdiploma.presentation.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomdiploma.R
import com.example.ecomdiploma.domain.shopfrag.SimpleProduct

class CartAdapter(
    private var items: List<SimpleProduct>,
    private val onDeleteClick: (SimpleProduct) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.prodImageView)
        val name: TextView = view.findViewById(R.id.prodNameText)
        val price: TextView = view.findViewById(R.id.prodPriceText)
        val size: TextView = view.findViewById(R.id.textSizeValue)
        val delete: ImageView = view.findViewById(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = items[position]
        holder.image.setImageResource(product.imageResId)
        holder.name.text = product.name
        holder.price.text = product.price
        holder.size.text = product.size

        holder.delete.setOnClickListener {
            onDeleteClick(product)
        }
    }

    override fun getItemCount() = items.size
    fun updateItems(newItems: List<SimpleProduct>) {
        items = newItems
        notifyDataSetChanged()
    }
}