package com.example.pruebatecnicaapp.data.network

import com.example.pruebatecnicaapp.data.model.CustomerDto
import com.example.pruebatecnicaapp.data.model.OrderDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
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

    /** Devuelve un List<OrderDto> para un customer dado */
    suspend fun getOrders(customerId: String): List<OrderDto> {
        return client
            .get("$baseUrl/customer/$customerId/orders")
            .body<List<OrderDto>>()
    }
}
