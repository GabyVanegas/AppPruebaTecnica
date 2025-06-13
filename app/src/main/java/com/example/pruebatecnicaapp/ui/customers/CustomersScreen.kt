package com.example.pruebatecnicaapp.ui.customers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicaapp.data.model.CustomerDto
import com.example.pruebatecnicaapp.viewmodel.*

@Composable
fun CustomersScreen(viewModel: MainViewModel, onCustomerClick: (String) -> Unit) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadCustomers() }

    when (state) {
        is Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is CustomersLoaded -> {
            val list = (state as CustomersLoaded).list
            LazyColumn {
                items(list) { customer ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { onCustomerClick(customer.id) },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(text = customer.companyName, style = MaterialTheme.typography.titleMedium)
                            Text(text = customer.contactName ?: "", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
        is Error -> Text((state as Error).message, color = MaterialTheme.colorScheme.error)
        else -> {}
    }
}