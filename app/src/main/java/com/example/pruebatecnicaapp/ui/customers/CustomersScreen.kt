package com.example.pruebatecnicaapp.ui.customers

import androidx.compose.foundation.clickable
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
import com.example.pruebatecnicaapp.data.model.CustomerDto
import com.example.pruebatecnicaapp.viewmodel.*

@Composable
fun CustomersScreen(viewModel: MainViewModel, onCustomerClick: (String) -> Unit) {
    val state by viewModel.uiState.collectAsState()

    var filter by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var editingCustomer by remember { mutableStateOf<CustomerDto?>(null) }

    LaunchedEffect(filter) {
        viewModel.loadCustomers(filter.ifBlank { null })
    }

    Scaffold(
        topBar = {
            OutlinedTextField(
                value = filter,
                onValueChange = { filter = it },
                label = { Text("Filtrar por país") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingCustomer = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar cliente")
            }
        }
    ) { padding ->

        Box(Modifier.fillMaxSize().padding(padding)) {
            when (state) {
                is Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                is CustomersLoaded -> {
                    val list = (state as CustomersLoaded).list
                    LazyColumn {
                        items(list) { customer ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                ListItem(
                                    headlineContent = { Text(customer.companyName) },
                                    supportingContent = { Text(customer.contactName ?: "") },
                                    modifier = Modifier.clickable { onCustomerClick(customer.id) },
                                    trailingContent = {
                                        Row {
                                            IconButton(onClick = {
                                                editingCustomer = customer
                                                showDialog = true
                                            }) {
                                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                                            }
                                            IconButton(onClick = {
                                                viewModel.deleteCustomer(customer.id) {
                                                    viewModel.loadCustomers(filter.ifBlank { null })
                                                }
                                            }) {
                                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                            }
                                        }
                                    }
                                )
                            }
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
                CustomerDialog(
                    customer = editingCustomer,
                    onDismiss = { showDialog = false },
                    onSave = { customer ->
                        viewModel.saveCustomer(customer) {
                            showDialog = false
                            viewModel.loadCustomers(filter.ifBlank { null })
                        }
                    }
                )
            }
        }
    }
}