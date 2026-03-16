package com.example.ecomdiploma.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.ecomdiploma.domain.shopfrag.SimpleProduct
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val _cartItems = MutableLiveData<List<SimpleProduct>>(emptyList())
    val cartItems: LiveData<List<SimpleProduct>> get() = _cartItems
    val cartItemCount: LiveData<Int> = _cartItems.map { it.size }
    val totalPrice: LiveData<Double> = _cartItems.map { items ->
        items.sumOf { it.price.replace("$", "").replace(",", ".").toDoubleOrNull() ?: 0.0 }
    }

    private val sharedPrefs = application.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
    private val KEY_CART_ITEMS = "cart_items"

    init {
        val savedJson = sharedPrefs.getString(KEY_CART_ITEMS, null)

        if (!savedJson.isNullOrBlank()) {
            try {
                val type = object : TypeToken<List<SimpleProduct>>() {}.type
                val items: List<SimpleProduct> = Gson().fromJson(savedJson, type)
                _cartItems.value = items
            } catch (e: Exception) {
                _cartItems.value = emptyList()
            }
        } else {
            _cartItems.value = emptyList()
        }
    }

    fun addItemToCart(product: SimpleProduct) {
        val current = _cartItems.value.orEmpty()
        if (current.any { it.name == product.name && it.size == product.size && it.imageResId == product.imageResId }) {
            return
        }
        val updated = current + product
        _cartItems.value = updated
        saveCartItems(updated)
    }

    fun removeItem(product: SimpleProduct) {
        val updated = _cartItems.value.orEmpty().filterNot { it.name == product.name && it.size == product.size && it.imageResId == product.imageResId}
        _cartItems.value = updated
        saveCartItems(updated)
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        saveCartItems(emptyList())
    }

    private fun saveCartItems(items: List<SimpleProduct>) {
        val json = Gson().toJson(items)
        sharedPrefs.edit().putString(KEY_CART_ITEMS, json).apply()
    }
}