package com.example.ecomdiploma.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.ecomdiploma.domain.shopfrag.AddProductToCartUseCase
import com.example.ecomdiploma.domain.shopfrag.GetSavedProdUseCase
import com.example.ecomdiploma.domain.shopfrag.SimpleProductModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartViewModel(
    application: Application,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getSavedProdUseCase: GetSavedProdUseCase
) : AndroidViewModel(application) {

    private val _cartItems = MutableLiveData<List<SimpleProductModel>>(emptyList())
    val cartItems: LiveData<List<SimpleProductModel>> get() = _cartItems
    val cartItemCount: LiveData<Int> = _cartItems.map { it.size }
    val totalPrice: LiveData<Double> = _cartItems.map { items ->
        items.sumOf { it.price.replace("$", "").replace(",", ".").toDoubleOrNull() ?: 0.0 }
    }

    suspend fun loadSavedCart() {
        try {
            delay(100)
            val savedCart = getSavedProdUseCase.getProducts()
            _cartItems.value = savedCart
        } catch (e: Exception) {
            Log.e("CartViewModel", "Error", e)
        }
    }

    fun addItemToCart(product: SimpleProductModel) {
        val current = _cartItems.value.orEmpty()
        if (current.any { it.name == product.name && it.size == product.size && it.imageResId == product.imageResId }) {
            return
        }
        val updated = current + product
        _cartItems.value = updated
        saveCartItemsUpdated(updated)
    }

    fun removeItem(product: SimpleProductModel) {
        val updated = _cartItems.value.orEmpty().filterNot { it.name == product.name && it.size == product.size && it.imageResId == product.imageResId }
        _cartItems.value = updated
        saveCartItemsUpdated(updated)
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        saveCartItemsUpdated(emptyList())
    }

    private fun saveCartItemsUpdated(items: List<SimpleProductModel>) {
        viewModelScope.launch {
            try {
                delay(10)
                addProductToCartUseCase.addProducts(items)
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error", e)
            }
        }
    }
}