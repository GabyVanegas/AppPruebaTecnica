package com.example.pruebatecnicaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}