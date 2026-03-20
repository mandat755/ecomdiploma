package com.example.ecomdiploma.data.productdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_products")
data class SimpleProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val name: String,
    val price: String,
    val size: String,
    val imageResId: Int
): java.io.Serializable