package com.example.ecomdiploma.server

import com.example.ecomdiploma.data.productdatabase.ProductEntity
import com.example.ecomdiploma.domain.shopfrag.SimpleProductModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("/products")
    suspend fun getProducts(): List<ProductEntity>
    @GET("/productsForCart")
    suspend fun getProductsForCart(): List<SimpleProductModel>
    @GET("/products")
    suspend fun getFeeds(@Query("name") name: String, @Query("keyWord") keyWord: String): List<ProductEntity>
    @POST("/addProduct")
    suspend fun addBrand(@Query("name") brandName: String): Response<Unit>
    @POST("/saveProducts")
    suspend fun saveProducts(@Body products: List<SimpleProductModel>): Response<Unit>
}

object RetrofitClient {
    private const val BASE_URL = "http://localhost:8081"

    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}