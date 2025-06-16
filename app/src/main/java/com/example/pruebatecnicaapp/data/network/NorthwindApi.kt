package com.example.pruebatecnicaapp.data.network

import com.example.pruebatecnicaapp.data.model.CustomerDto
import com.example.pruebatecnicaapp.data.model.OrderDto
import com.example.pruebatecnicaapp.data.model.OrderPostDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NorthwindApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            // usa el json de kotlinx.serialization
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    // apuntando al emulador Android → localhost:8080
    private val baseUrl = "http://10.0.2.2:8080"

    /** Devuelve directamente un List<CustomerDto> del endpoint /customers */
    suspend fun getCustomers(country: String? = null): List<CustomerDto> {
        val url = buildString {
            append("$baseUrl/customers")
            country?.let { append("?country=${it}") }
        }
        // fuerza la inferencia del tipo reificado si hiciera falta:
        return client
            .get(url)
            .body<List<CustomerDto>>()
    }

    // NUEVOS métodos para Customers
    suspend fun addCustomer(customer: CustomerDto): HttpResponse {
        return client.post("$baseUrl/customers") {
            contentType(ContentType.Application.Json)
            setBody(customer)
        }
    }

    suspend fun updateCustomer(customerId: String, customer: CustomerDto): HttpResponse {
        return client.put("$baseUrl/customers/$customerId") {
            contentType(ContentType.Application.Json)
            setBody(customer)
        }
    }

    suspend fun deleteCustomer(customerId: String): HttpResponse {
        return client.delete("$baseUrl/customers/$customerId")
    }

    suspend fun customerExists(customerId: String): Boolean {
        return try {
            client.get("$baseUrl/customers/$customerId").status == HttpStatusCode.OK
        } catch (e: Exception) {
            false
        }
    }

    /** Devuelve un List<OrderDto> para un customer dado */
    suspend fun getOrders(customerId: String): List<OrderDto> {
        return client
            .get("$baseUrl/customer/$customerId/orders")
            .body<List<OrderDto>>()
    }

    suspend fun getOrdersByCustomerId(customerId: String): List<OrderDto> {
        return client.get("$baseUrl/customer/$customerId/orders").body()
    }

    suspend fun addOrder(order: OrderPostDto): HttpResponse {
        return client.post("$baseUrl/orders") {
            contentType(ContentType.Application.Json)
            setBody(order)
        }
    }

    suspend fun updateOrder(orderId: Int, order: OrderPostDto): HttpResponse {
        return client.put("$baseUrl/orders/$orderId") {
            contentType(ContentType.Application.Json)
            setBody(order)
        }
    }

    suspend fun deleteOrder(orderId: Int): HttpResponse {
        return client.delete("$baseUrl/orders/$orderId")
    }

    suspend fun orderExists(orderId: Int): Boolean {
        return try {
            client.get("$baseUrl/orders/$orderId").status == HttpStatusCode.OK
        } catch (e: Exception) {
            false
        }
    }
}
