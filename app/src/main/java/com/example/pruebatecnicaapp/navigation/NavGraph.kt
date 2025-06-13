package com.example.pruebatecnicaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pruebatecnicaapp.ui.customers.CustomersScreen
import com.example.pruebatecnicaapp.ui.orders.OrdersScreen
import com.example.pruebatecnicaapp.viewmodel.MainViewModel

@Composable
fun NavGraph(vm: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "customers"
    ) {
        // Pantalla de clientes
        composable("customers") {
            CustomersScreen(vm) { customerId ->
                navController.navigate("orders/$customerId")
            }
        }
        // Pantalla de órdenes
        composable(
            route = "orders/{customerId}",
            arguments = listOf(navArgument("customerId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId")!!
            OrdersScreen(vm, customerId)
        }
    }
}