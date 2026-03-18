package com.example.ecomdiploma.domain.shopfrag

import android.util.Log
import com.example.ecomdiploma.data.shopfrag.ShopProductMapper.toModel
import com.example.ecomdiploma.server.ApiService

class GetAllProductUseCase(private val apiService: ApiService) {
    suspend fun getAllProducts(): List<ProductModel> { //ProductModel
        return apiService.getProducts().map {
            Log.d("MyRoomLog", "$it")
            it.toModel() }
    }
}
