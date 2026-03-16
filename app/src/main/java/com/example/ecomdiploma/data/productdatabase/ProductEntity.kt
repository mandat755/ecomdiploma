package com.example.ecomdiploma.data.productdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "Product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: String,
    val description: String,
    val images: List<Int>
) : java.io.Serializable
// Конвертер (залишаємо без змін)
class ImagesTypeConverter {
    @TypeConverter
    fun fromImagesList(images: List<Int>): String {
        return images.joinToString(",")
    }

    @TypeConverter
    fun toImagesList(images: String): List<Int> {
        return if (images.isEmpty()) emptyList() else images.split(",").map { it.toInt() }
    }
}