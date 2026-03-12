package com.example.ecomdiploma.presentation.fragments.shopfrag

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomdiploma.R

class ProductAdapter(
    private val products: List<Product>,
    private val onItemClickListener: (Product) -> Unit,
    private val layout: Int
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val productName: TextView = view.findViewById(R.id.productName)
        private val productPrice: TextView = view.findViewById(R.id.productPrice)
        private val productImage: ImageView = view.findViewById(R.id.productImage)

        init {
            view.setOnClickListener {
                val product = products[adapterPosition]
                onItemClickListener(product)
            }
        }

        fun bind(product: Product) {
            Log.d("ProductImage", product.images.toString())
            Log.d("ProductImage2", "${R.drawable.gripboots1}")
            Log.d("ProductImage2", "${R.drawable.gripboots2}")
            Log.d("ProductImage2", "${R.drawable.gripboots3}")
            Log.d("ProductImage2", "${R.drawable.gripboots4}")
            productName.text = product.name
            productPrice.text = product.price
            if (product.images.isNotEmpty()) {
                productImage.setImageResource(product.images[0])
            }
        }
    }
}

data class Product(
    val name: String,
    val price: String,
    val images: List<Int>,
    val description: String
): java.io.Serializable

data class SimpleProduct(
    val name: String,
    val price: String,
    val size: String,
    val imageResId: Int
): java.io.Serializable