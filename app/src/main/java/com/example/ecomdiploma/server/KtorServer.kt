package com.example.ecomdiploma.server

import android.content.Context
import android.util.Log
import com.example.ecomdiploma.data.database.ProductsDB
import com.example.ecomdiploma.data.productdatabase.ProductEntity
import com.example.ecomdiploma.domain.shopfrag.ProductModel
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Application.module(context: Context) {
    install(ContentNegotiation) {
        gson()
    }

    routing {
        get("/feedbrands") {
            try {
                val specs = ProductsDB.getDb(context).getDao().getProducts()

                if (specs.isNotEmpty()) {
                    call.respond(specs)
                } else {
                    call.respond(emptyList<ProductEntity>())
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Помилка пошуку брендів: $e")
            }
        }
        get("/products") {
            Log.d("MyRoomLog", "Тут 2")
            call.respond(listOf(ProductEntity(name = "1", price = "50", description = "desc", images = listOf(1))))
        }
        post("/addBrand") {
            val brandName = call.request.queryParameters["brandName"]

            if (brandName.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "Немає 'brandName'")
                return@post
            }

            try {
                val dao = ProductsDB.getDb(context).getDao()
                //val entity = ProductModel(name = brandName, brandLogo = 6, cardBackColor = 5, textColor = 2).toEntity()
                //dao.insertBrand(entity)

                call.respond(HttpStatusCode.OK, "Бренд створено")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Помилка створення бренду: ${e.message}")
            }
        }
        post("/addFeed") {
            val feedString = call.request.queryParameters["spec"]
            val tempList = feedString?.split("/")

            try {
                if (tempList != null) {
                    //val feed = ProductModel(...)
                    val dao = ProductsDB.getDb(context).getDao()
                    //val en = feed.toEntity()
                    //dao.insertFeed(en)
                }

                call.respond(HttpStatusCode.OK, "Специфікацію створено")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Помилка створення специфікації: ${e.message}")
            }
        }
    }
}

object KtorServer {

    var server: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>? = null
        private set

    fun startServer(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            if (server == null) {
                server = embeddedServer(
                    Netty,
                    host = "localhost",
                    port = 8081,
                    module = { module(context) }
                )
                server?.start(wait = false)
                Log.d("MyRoomLog", "Тут 1")// ← ВИПРАВЛЕНО (окремий виклик)
            }
        }
    }

    fun stopServer() {
        server?.stop(1000, 2000)
        server = null
    }
}