package com.example.ecomdiploma.server

import android.content.Context
import android.util.Log
import com.example.ecomdiploma.data.database.ProductsDB
import com.example.ecomdiploma.data.productdatabase.ProductEntity
import com.example.ecomdiploma.data.shopfrag.CartProductMapper.toEntity
import com.example.ecomdiploma.data.shopfrag.CartProductMapper.toModel
import com.example.ecomdiploma.data.shopfrag.ShopProductMapper.toModel
import com.example.ecomdiploma.domain.shopfrag.ProductModel
import com.example.ecomdiploma.domain.shopfrag.SimpleProductModel
import com.example.ecomdiploma.R
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.stop
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

    /**
     * Найправильніший і стабільний запуск сервера.
     * Чекає реального старту перед тим, як повернути контроль.
     */
    suspend fun startServerAndWait(context: Context) {
        if (server != null) {
            Log.d("MyRoomLog", "Сервер вже запущений")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            server = embeddedServer(
                Netty,
                host = "localhost",           // ОБОВ'ЯЗКОВО 0.0.0.0 !!!
                port = 8081
            ) {
                // ← Тут ми всередині Application.() -> Unit
                module(context)   // викликаємо наш модуль з усіма роутами

                // Підписка на подію старту — тепер працює 100%
                environment.monitor.subscribe(ApplicationStarted) {
                    serverStarted.complete(Unit)
                    Log.d("MyRoomLog", "✅ Ktor сервер повністю запущений і готовий приймати запити")
                }
            }

            server?.start(wait = false)
        }

        // Реально чекаємо, поки сервер прив'яже порт
        serverStarted.await()
    }

    fun stopServer() {
        server?.stop(1000, 2000)
        server = null
        serverStarted.cancel()
    }

    // ===================================================================
    // ВСІ РОУТИ (точно ті самі, що були у тебе)
    // ===================================================================
    private fun Application.module(context: Context) {
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
                try {
                    Log.d("MyRoomLog", "Тут 2")

                    val dao = ProductsDB.getDb(context).getDao()
                    val products = dao.getProducts()

                    Log.d("MyRoomLog", "count = ${products.size}")
                    Log.d("MyRoomLog", "products = $products")

                    call.respond(products)
                } catch (e: Exception) {
                    Log.e("MyRoomLog", "Помилка в /products", e)
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "error")
                }
            }

            get("/productsForCart") {
                try {
                    Log.d("MyRoomLog", "Тут 2")

                    val dao = ProductsDB.getDb(context).getDao()
                    val products = dao.getProductForCart()?.map { it.toModel() }

                    Log.d("MyRoomLog", "count = ${products?.size}")
                    Log.d("MyRoomLog", "products = $products")

                    call.respond(products ?: emptyList())
                } catch (e: Exception) {
                    Log.e("MyRoomLog", "Помилка в /products", e)
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "error")
                }
            }

            post("/addBrand") {
                val brandName = call.request.queryParameters["brandName"]

                if (brandName.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Немає 'brandName'")
                    return@post
                }

                try {
                    val dao = ProductsDB.getDb(context).getDao()
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
                        val dao = ProductsDB.getDb(context).getDao()
                    }
                    call.respond(HttpStatusCode.OK, "Специфікацію створено")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Помилка створення специфікації: ${e.message}")
                }
            }

            post("/saveProducts") {
                try {
                    val productsList: List<SimpleProductModel> = call.receive()

                    val dao = ProductsDB.getDb(context).getDao()

                    // 1. Очищаємо таблицю **ОДИН раз** перед вставкою
                    dao.deleteCart()

                    // 2. Вставляємо всі товари (якщо список не порожній)
                    if (productsList.isNotEmpty()) {
                        productsList.forEach { prod ->
                            dao.insertProduct(prod.toEntity())
                        }
                    }

                    call.respond(HttpStatusCode.OK, "Збережено ${productsList.size} товарів")
                } catch (e: Exception) {
                    Log.e("Mylogcartloh", "Помилка збереження: ${e.message}")
                    call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
                }
            }
        }
    }
}