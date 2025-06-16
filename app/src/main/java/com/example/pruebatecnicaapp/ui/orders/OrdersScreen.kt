package com.example.pruebatecnicaapp.ui.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicaapp.data.model.OrderPostDto
import com.example.pruebatecnicaapp.viewmodel.MainViewModel
import com.example.pruebatecnicaapp.viewmodel.Loading
import com.example.pruebatecnicaapp.viewmodel.OrdersLoaded
import com.example.pruebatecnicaapp.viewmodel.Error
import com.example.pruebatecnicaapp.ui.orders.OrderDialog

@Composable
fun OrdersScreen(viewModel: MainViewModel, customerId: String) {
    val state by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingOrder by remember { mutableStateOf<OrderPostDto?>(null) }

    LaunchedEffect(customerId) { viewModel.loadOrders(customerId) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingOrder = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->

        Box(Modifier.fillMaxSize().padding(padding)) {
            when (state) {
                is Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                is OrdersLoaded -> {
                    val list = (state as OrdersLoaded).list
                    LazyColumn {
                        items(list) { order ->
                            ListItem(
                                headlineContent = { Text("Order ${order.orderId}") },
                                supportingContent = {
                                    Text("Shipped: ${order.shippedDate ?: "—"}")
                                },
                                trailingContent = {
                                    Row {
                                        IconButton(onClick = {
                                            editingOrder = OrderPostDto(
                                                orderId = order.orderId,
                                                customerId = customerId,
                                                orderDate = order.orderDate,
                                                shippedDate = order.shippedDate
                                            )
                                            showDialog = true
                                        }) {
                                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                                        }
                                        IconButton(onClick = {
                                            viewModel.deleteOrder(order.orderId) {
                                                viewModel.loadOrders(customerId)
                                            }
                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                                        }
                                    }
                                },
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

            if (showDialog) {
                OrderDialog(
                    order = editingOrder,
                    customerId = customerId,
                    onDismiss = { showDialog = false },
                    onSave = { order ->
                        viewModel.saveOrder(order) {
                            showDialog = false
                            viewModel.loadOrders(customerId)
                        }
                    }
                )
            }
        }
    }
}
