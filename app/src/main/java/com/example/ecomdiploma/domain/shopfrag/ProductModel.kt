package com.example.ecomdiploma.domain.shopfrag

data class ProductModel(
    val id: Int = 0,
    val name: String,
    val price: String,
    val images: List<Int>,
    val description: String) : java.io.Serializable