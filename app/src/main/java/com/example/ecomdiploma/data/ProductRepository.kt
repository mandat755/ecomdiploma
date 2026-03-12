package com.example.ecomdiploma.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.ecomdiploma.presentation.fragments.shopfrag.Product
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ProductRepository(context: Context) : SQLiteOpenHelper(context, "app_database.db", null, 1) {
    val dbPath = context.getDatabasePath("app_database.db").path

    init {
        // Перевірка, чи база даних скопійована, якщо її немає
        if (!File(dbPath).exists()) {
            copyDatabaseFromAssets(context)
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS Product (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                price TEXT NOT NULL,
                description TEXT NOT NULL,
                images TEXT NOT NULL
            );
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Product")
        onCreate(db)
    }

    // Змінюємо доступ на public або internal
    internal fun copyDatabaseFromAssets(context: Context) {
        val assetManager = context.assets
        val inputStream: InputStream
        val outputStream: OutputStream
        try {
            inputStream = assetManager.open("app_database.db")
            outputStream = FileOutputStream(dbPath)

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            Log.e("Database", "Error copying database: ${e.message}")
        }
    }

    fun getAllProducts(): List<Product> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Product", null)

        val products = mutableListOf<Product>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val price = cursor.getString(cursor.getColumnIndex("price"))
            val description = cursor.getString(cursor.getColumnIndex("description"))
            val imagesString = cursor.getString(cursor.getColumnIndex("images"))
            val images = imagesString.split(",").map { it.toInt() }

            products.add(Product(name, price, images, description))
        }
        cursor.close()
        db.close()
        return products
    }
}