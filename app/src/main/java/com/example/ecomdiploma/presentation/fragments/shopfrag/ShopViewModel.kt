package com.example.ecomdiploma.presentation.fragments.shopfrag

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomdiploma.domain.shopfrag.GetAllProductUseCase
import com.example.ecomdiploma.domain.shopfrag.ProductModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ShopViewModel(private val getAllProductUseCase: GetAllProductUseCase): ViewModel() {
    private val _allProductModelList = MutableLiveData<List<ProductModel>>()
    val allProductModelList: LiveData<List<ProductModel>> = _allProductModelList

    fun getAllProduct() {
        viewModelScope.launch {
            try {
                delay(10)
                _allProductModelList.postValue(getAllProductUseCase.getAllProducts())
            } catch (e: Exception) {
                Log.e("ShopViewModel", "Error", e)
            }
        }
    }
}