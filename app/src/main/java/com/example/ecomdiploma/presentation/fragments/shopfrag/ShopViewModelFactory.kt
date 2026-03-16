package com.example.ecomdiploma.presentation.fragments.shopfrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecomdiploma.domain.shopfrag.GetAllProductUseCase

class ShopViewModelFactory (private val getAllProductUseCase: GetAllProductUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            return ShopViewModel(getAllProductUseCase) as T
        }
        throw IllegalArgumentException("Невідомий ViewModel-клас")
    }
}