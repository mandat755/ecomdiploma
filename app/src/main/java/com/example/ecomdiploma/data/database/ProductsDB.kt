package com.example.ecomdiploma.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ecomdiploma.data.productdatabase.Dao
import com.example.ecomdiploma.data.productdatabase.ImagesTypeConverter
import com.example.ecomdiploma.data.productdatabase.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(ImagesTypeConverter::class)
abstract class ProductsDB : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): ProductsDB {
            return Room.databaseBuilder(
                context.applicationContext,
                ProductsDB::class.java,
                "app_database.db"
            ).createFromAsset("app_database.db") // Переконайтесь, що файл існує в assets
                .build()
        }
    }
}