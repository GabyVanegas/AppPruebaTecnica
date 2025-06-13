package com.example.pruebatecnicaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pruebatecnicaapp.di.AppModule
import com.example.pruebatecnicaapp.ui.theme.AppTheme
import com.example.pruebatecnicaapp.navigation.NavGraph
import com.example.pruebatecnicaapp.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels { AppModule.provideMainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                NavGraph(viewModel)
            }
        }
    }
}