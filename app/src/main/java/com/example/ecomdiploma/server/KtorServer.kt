package com.example.ecomdiploma.server

import android.content.Context
import com.example.ecomdiploma.data.database.ProductsDB
import com.example.ecomdiploma.data.shopfrag.CartProductMapper.toEntity
import com.example.ecomdiploma.data.shopfrag.CartProductMapper.toModel
import com.example.ecomdiploma.domain.shopfrag.SimpleProductModel
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.install
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object KtorServer {

    var server: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>? = null
        private set

    private val serverStarted = CompletableDeferred<Unit>()

    suspend fun startServerAndWait(context: Context) {
        if (server != null) {
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            server = embeddedServer(
                Netty,
                host = "localhost",
                port = 8081
            ) {
                module(context)

                environment.monitor.subscribe(ApplicationStarted) {
                    serverStarted.complete(Unit)
                }
            }

            server?.start(wait = false)
        }
        serverStarted.await()
    }

    fun stopServer() {
        server?.stop(1000, 2000)
        server = null
        serverStarted.cancel()
    }

    private fun Application.module(context: Context) {
        install(ContentNegotiation) {
            gson()
        }

        routing {
            get("/products") {
                try {
                    val dao = ProductsDB.getDb(context).getDao()
                    val products = dao.getProducts()

                    call.respond(products)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "error")
                }
            }
            get("/productsForCart") {
                try {
                    val dao = ProductsDB.getDb(context).getDao()
                    val products = dao.getProductForCart()?.map { it.toModel() }

                    call.respond(products ?: emptyList())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "error")
                }
            }
            post("/saveProducts") {
                try {
                    val productsList: List<SimpleProductModel> = call.receive()
                    val dao = ProductsDB.getDb(context).getDao()

                    dao.deleteCart()

                    if (productsList.isNotEmpty()) {
                        productsList.forEach { prod ->
                            dao.insertProduct(prod.toEntity())
                        }
                    }

                    call.respond(HttpStatusCode.OK, "Saved ${productsList.size} products")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
                }
            }
        }
    }
}