package com.example.pruebatecnicaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicaapp.data.model.CustomerDto
import com.example.pruebatecnicaapp.data.model.OrderPostDto
import com.example.pruebatecnicaapp.data.repository.NorthwindRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: NorthwindRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun loadCustomers(country: String? = null) {
        viewModelScope.launch {
            _uiState.value = Loading
            try {
                val customers = repository.fetchCustomers(country)
                _uiState.value = CustomersLoaded(customers)
            } catch (e: Exception) {
                _uiState.value = Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun saveCustomer(customer: CustomerDto, onFinished: () -> Unit) {
        viewModelScope.launch {
            try {
                if (repository.customerExists(customer.id)) {
                    repository.updateCustomer(customer.id, customer)
                } else {
                    repository.addCustomer(customer)
                }
                onFinished()
            } catch (e: Exception) {
                _uiState.value = Error("Error guardando cliente: ${e.message}")
            }
        }
    }

    fun deleteCustomer(customerId: String, onFinished: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteCustomer(customerId)
                onFinished()
            } catch (e: Exception) {
                _uiState.value = Error("Error eliminando cliente: ${e.message}")
            }
        }
    }


    fun loadOrders(customerId: String) {
        viewModelScope.launch {
            _uiState.value = Loading
            try {
                val orders = repository.fetchOrders(customerId)
                _uiState.value = OrdersLoaded(orders)
            } catch (e: Exception) {
                _uiState.value = Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun saveOrder(order: OrderPostDto, onFinished: () -> Unit) {
        viewModelScope.launch {
            try {
                if (repository.orderExists(order.orderId)) {
                    repository.updateOrder(order.orderId, order)
                } else {
                    repository.addOrder(order)
                }
                onFinished()
            } catch (e: Exception) {
                _uiState.value = Error("Error guardando orden: ${e.message}")
            }
        }
    }


    fun deleteOrder(orderId: Int, onFinished: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteOrder(orderId)
                onFinished()
            } catch (e: Exception) {
                _uiState.value = Error("Error eliminando orden: ${e.message}")
            }
        }
    }
}