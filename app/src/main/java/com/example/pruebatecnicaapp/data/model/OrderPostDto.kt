package com.example.pruebatecnicaapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderPostDto(
    val orderId: Int,
    val customerId: String,
    val orderDate: String? = null,
    val shippedDate: String? = null
)