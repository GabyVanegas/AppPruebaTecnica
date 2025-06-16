package com.example.pruebatecnicaapp.data.repository

import com.example.pruebatecnicaapp.data.model.CustomerDto
import com.example.pruebatecnicaapp.data.model.OrderDto
import com.example.pruebatecnicaapp.data.model.OrderPostDto
import com.example.pruebatecnicaapp.data.network.NorthwindApi

class NorthwindRepository(private val api: NorthwindApi) {
    suspend fun fetchCustomers(country: String? = null): List<CustomerDto> =
        api.getCustomers(country)


    suspend fun addCustomer(customer: CustomerDto) =
        api.addCustomer(customer)

    suspend fun updateCustomer(customerId: String, customer: CustomerDto) =
        api.updateCustomer(customerId, customer)

    suspend fun deleteCustomer(customerId: String) =
        api.deleteCustomer(customerId)

    suspend fun customerExists(customerId: String): Boolean =
        api.customerExists(customerId)

    suspend fun fetchOrders(customerId: String): List<OrderDto> =
        api.getOrders(customerId)

    suspend fun addOrder(order: OrderPostDto) =
        api.addOrder(order)

    suspend fun updateOrder(orderId: Int, order: OrderPostDto) =
        api.updateOrder(orderId, order)

    suspend fun deleteOrder(orderId: Int) =
        api.deleteOrder(orderId)

    suspend fun orderExists(orderId: Int): Boolean {
        return api.orderExists(orderId)
    }

    // Si decides implementar un endpoint general GET /orders, puedes usar esto:
    private suspend fun fetchAllOrders(): List<OrderDto> {
        // Lógica a implementar si tienes un endpoint para obtener todas las órdenes
        // De momento lanza excepción para que no se use directamente sin definir
        throw NotImplementedError("No hay endpoint GET /orders aún definido")
    }

}
