package com.example.pruebatecnicaapp.data.repository

import com.example.pruebatecnicaapp.data.model.CustomerDto
import com.example.pruebatecnicaapp.data.model.OrderDto
import com.example.pruebatecnicaapp.data.network.NorthwindApi

class NorthwindRepository(private val api: NorthwindApi) {
    suspend fun fetchCustomers(country: String? = null): List<CustomerDto> =
        api.getCustomers(country)

    suspend fun fetchOrders(customerId: String): List<OrderDto> =
        api.getOrders(customerId)

}
