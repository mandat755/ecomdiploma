package com.example.ecomdiploma.presentation.mainactivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecomdiploma.domain.shopfrag.AddProductToCartUseCase
import com.example.ecomdiploma.domain.shopfrag.GetSavedProdUseCase
import com.example.ecomdiploma.presentation.viewmodel.CartViewModel

class CartViewModelFactory(
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getSavedProdUseCase: GetSavedProdUseCase,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(application, addProductToCartUseCase, getSavedProdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel-class")
    }
}