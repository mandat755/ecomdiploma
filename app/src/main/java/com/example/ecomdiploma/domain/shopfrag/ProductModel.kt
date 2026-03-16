package com.example.ecomdiploma.domain.shopfrag

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverters

data class ProductModel(
    val id: Int = 0,
    val name: String,
    val price: String,
    val images: List<Int>,
    val description: String) : java.io.Serializable