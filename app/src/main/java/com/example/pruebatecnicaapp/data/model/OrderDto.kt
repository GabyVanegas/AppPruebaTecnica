package com.example.pruebatecnicaapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto (
    val orderId: Int,
    val orderDate: String? = null,
    val shippedDate: String? = null
)