package com.example.pruebatecnicaapp.data.model
import kotlinx.serialization.Serializable

@Serializable
data class CustomerDto(
    val id: String,
    val companyName: String,
    val contactName: String? = null,
    val country: String? = null
)

