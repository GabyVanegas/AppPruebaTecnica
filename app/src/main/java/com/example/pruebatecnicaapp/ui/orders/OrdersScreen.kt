package com.example.pruebatecnicaapp.ui.orders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicaapp.viewmodel.*

@Composable
fun OrdersScreen(viewModel: MainViewModel, customerId: String) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(customerId) { viewModel.loadOrders(customerId) }

    when (state) {
        is Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is OrdersLoaded -> {
            val list = (state as OrdersLoaded).list
            LazyColumn {
                items(list) { order ->
                    ListItem(
                        headlineContent = { Text(text = "Order ${order.orderId}") },
                        supportingContent = { Text(text = "Shipped: ${order.shippedDate ?: "—"}") },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }

        is Error -> Text(
            text = (state as Error).message,
            color = MaterialTheme.colorScheme.error
        )

        else -> {}
    }
}