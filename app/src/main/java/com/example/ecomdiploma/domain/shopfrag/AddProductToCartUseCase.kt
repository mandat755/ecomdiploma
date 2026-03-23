package com.example.ecomdiploma.domain.shopfrag

import com.example.ecomdiploma.server.ApiService
import retrofit2.Response

class AddProductToCartUseCase(private val apiService: ApiService) {
    suspend fun addProducts(items: List<SimpleProductModel>): Response<Unit> {
        return apiService.saveProducts(items)
    }
}