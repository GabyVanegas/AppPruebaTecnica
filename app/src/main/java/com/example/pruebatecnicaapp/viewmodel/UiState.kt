package com.example.pruebatecnicaapp.viewmodel

import com.example.pruebatecnicaapp.data.model.CustomerDto
import com.example.pruebatecnicaapp.data.model.OrderDto

sealed interface UiState
object Loading : UiState
data class CustomersLoaded(val list: List<CustomerDto>) : UiState
data class OrdersLoaded(val list: List<OrderDto>) : UiState
data class Error(val message: String) : UiState