package com.example.ecomdiploma.domain.shopfrag

import com.example.ecomdiploma.server.ApiService
import retrofit2.Response

class GetSavedProdUseCase(private val apiService: ApiService) {
    suspend fun getProducts(): List<SimpleProductModel> {
        return apiService.getProductsForCart()
    }
}