package com.example.ecomdiploma.presentation.fragments.shopfrag

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomdiploma.R
import com.example.ecomdiploma.domain.shopfrag.ProductModel

class ProductAdapter(
    private val productModels: List<ProductModel>,
    private val onItemClickListener: (ProductModel) -> Unit,
    private val layout: Int
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productModels[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productModels.size

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val productName: TextView = view.findViewById(R.id.productName)
        private val productPrice: TextView = view.findViewById(R.id.productPrice)
        private val productImage: ImageView = view.findViewById(R.id.productImage)

        init {
            view.setOnClickListener {
                val product = productModels[adapterPosition]
                onItemClickListener(product)
            }
        }

        fun bind(productModel: ProductModel) {
            Log.d("ProductImage", productModel.images.toString())
            Log.d("ProductImage2", "${R.drawable.gripboots1}")
            Log.d("ProductImage2", "${R.drawable.gripboots2}")
            Log.d("ProductImage2", "${R.drawable.gripboots3}")
            Log.d("ProductImage2", "${R.drawable.gripboots4}")
            productName.text = productModel.name
            productPrice.text = productModel.price
            if (productModel.images.isNotEmpty()) {
                productImage.setImageResource(productModel.images[0])
            }
        }
    }
}

