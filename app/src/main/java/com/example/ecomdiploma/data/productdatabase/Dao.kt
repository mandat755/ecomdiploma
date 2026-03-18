package com.example.ecomdiploma.data.productdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecomdiploma.data.productdatabase.ProductEntity

@Dao
interface Dao {
    // Замінили Object на конкретний тип ProductEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrand(product: ProductEntity)

    // Замінили Object на ProductEntity? (може бути null)
    @Query("SELECT * FROM Product WHERE name = :name LIMIT 1")
    suspend fun getProductByName(name: String): ProductEntity?

    // Замінили Object на List<ProductEntity> для отримання списку продуктів
    @Query("SELECT * FROM Product")
    suspend fun getProducts(): List<ProductEntity>
}