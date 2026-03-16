package com.example.ecomdiploma.data.productdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecomdiploma.data.productdatabase.ProductEntity

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrand(product: ProductEntity)

    @Query("SELECT * FROM Product WHERE name = :name LIMIT 1")
    suspend fun getProductByName(name: String): ProductEntity?

    @Query("SELECT * FROM Product")
    suspend fun getProducts(): List<ProductEntity>
}