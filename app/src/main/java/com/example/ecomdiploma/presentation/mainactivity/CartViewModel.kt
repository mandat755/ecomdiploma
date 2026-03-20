package com.example.ecomdiploma.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.ecomdiploma.domain.shopfrag.AddProductToCartUseCase
import com.example.ecomdiploma.domain.shopfrag.GetSavedProdUseCase
import com.example.ecomdiploma.domain.shopfrag.SimpleProductModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    private val sharedPrefs = application.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
    private val KEY_CART_ITEMS = "cart_items"

    // Видалено виклик loadSavedCart() з init

    // Функція, яку можна викликати пізніше, коли потрібно завантажити корзину
    suspend fun loadSavedCart() {
        try {
            // Додаємо затримку перед запитом, щоб сервер встиг стартувати
            delay(2000) // Затримка 2 секунди, можете підлаштувати
            val savedCart = getSavedProdUseCase.getProducts()
            _cartItems.value = savedCart // присвоєння значення в LiveData
        } catch (e: Exception) {
            Log.e("CartViewModel", "Помилка завантаження продуктів", e)
        }
    }

    fun addItemToCart(product: SimpleProductModel) {
        val current = _cartItems.value.orEmpty()
        if (current.any { it.name == product.name && it.size == product.size && it.imageResId == product.imageResId }) {
            Log.d("Dipp2", "1")
            return
        }
        Log.d("Dipp2", "2")
        val updated = current + product
        _cartItems.value = updated
        Log.d("Dipp2", "3 - ${_cartItems.value}")
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