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

    @Query("SELECT * FROM cart_products")
    suspend fun getProductForCart(): List<SimpleProductEntity>?

    // Замінили Object на List<ProductEntity> для отримання списку продуктів
    @Query("SELECT * FROM Product")
    suspend fun getProducts(): List<ProductEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: SimpleProductEntity)

    @Query("DELETE FROM cart_products")
    suspend fun deleteCart()

    @Query("DELETE FROM cart_products WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Int>)
}