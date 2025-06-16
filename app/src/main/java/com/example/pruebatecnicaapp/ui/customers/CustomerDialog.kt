package com.example.pruebatecnicaapp.ui.customers

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicaapp.data.model.CustomerDto

@Composable
fun CustomerDialog(
    customer: CustomerDto?,
    onDismiss: () -> Unit,
    onSave: (CustomerDto) -> Unit
) {
    var id by remember { mutableStateOf(customer?.id ?: "") }
    var companyName by remember { mutableStateOf(customer?.companyName ?: "") }
    var contactName by remember { mutableStateOf(customer?.contactName ?: "") }
    var country by remember { mutableStateOf(customer?.country ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (id.isNotBlank() && companyName.isNotBlank()) {
                        onSave(CustomerDto(id, companyName, contactName, country))
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = {
            Text(if (customer != null) "Editar Cliente" else "Nuevo Cliente")
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    label = { Text("Customer ID") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    enabled = customer == null // No editable en modo edición
                )
                OutlinedTextField(
                    value = companyName,
                    onValueChange = { companyName = it },
                    label = { Text("Company Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = contactName,
                    onValueChange = { contactName = it },
                    label = { Text("Contact Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    label = { Text("Country") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
