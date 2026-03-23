package com.example.ecomdiploma.data.productdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Query("SELECT * FROM Product WHERE name = :name LIMIT 1")
    suspend fun getProductByName(name: String): ProductEntity?

    @Query("SELECT * FROM cart_products")
    suspend fun getProductForCart(): List<SimpleProductEntity>?

    @Query("SELECT * FROM Product")
    suspend fun getProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: SimpleProductEntity)

    @Query("DELETE FROM cart_products")
    suspend fun deleteCart()

    @Query("DELETE FROM cart_products WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Int>)
}