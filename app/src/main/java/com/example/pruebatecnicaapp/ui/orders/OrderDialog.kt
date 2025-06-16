package com.example.pruebatecnicaapp.ui.orders

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicaapp.data.model.OrderPostDto
import java.util.*

@Composable
fun OrderDialog(
    order: OrderPostDto?,
    customerId: String,
    onDismiss: () -> Unit,
    onSave: (OrderPostDto) -> Unit
) {
    var orderId by remember { mutableStateOf(order?.orderId ?: 0) }
    var orderDate by remember { mutableStateOf(order?.orderDate ?: "") }
    var shippedDate by remember { mutableStateOf(order?.shippedDate ?: "") }

    val context = LocalContext.current

    // Función para mostrar picker de fecha y hora
    fun showDateTimePicker(context: Context, onDateTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                TimePickerDialog(
                    context,
                    { _: TimePicker, hour: Int, minute: Int ->
                        val result = String.format(
                            "%04d-%02d-%02dT%02d:%02d:00",
                            year, month + 1, day, hour, minute
                        )
                        onDateTimeSelected(result)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onSave(OrderPostDto(orderId, customerId, orderDate, shippedDate))
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        title = { Text(if (order != null) "Editar Orden" else "Nueva Orden") },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = orderId.toString(),
                    onValueChange = { orderId = it.toIntOrNull() ?: 0 },
                    label = { Text("Order ID") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedButton(
                    onClick = {
                        showDateTimePicker(context) { selected -> orderDate = selected }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text("Order Date: ${orderDate.ifBlank { "Seleccionar fecha y hora" }}")
                }

                OutlinedButton(
                    onClick = {
                        showDateTimePicker(context) { selected -> shippedDate = selected }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Shipped Date: ${shippedDate.ifBlank { "Seleccionar fecha y hora" }}")
                }
            }
        }
    )
}
