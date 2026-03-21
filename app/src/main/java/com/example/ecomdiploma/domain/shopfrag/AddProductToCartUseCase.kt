package com.example.ecomdiploma.domain.shopfrag

import android.util.Log
import com.example.ecomdiploma.server.ApiService
import retrofit2.Response

class AddProductToCartUseCase(private val apiService: ApiService) {
    suspend fun addProducts(items: List<SimpleProductModel>): Response<Unit> {
        Log.d("Mylogcartloh", "$items")
        return apiService.saveProducts(items)
    }
}