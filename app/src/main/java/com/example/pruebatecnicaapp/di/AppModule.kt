package com.example.pruebatecnicaapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pruebatecnicaapp.data.network.NorthwindApi
import com.example.pruebatecnicaapp.data.repository.NorthwindRepository
import com.example.pruebatecnicaapp.viewmodel.MainViewModel

object AppModule {
    private val api       by lazy { NorthwindApi() }
    private val repo      by lazy { NorthwindRepository(api) }

    /** Devuelve un factory que crea MainViewModel(repo) */
    fun provideMainViewModel(): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repo) as T
            }
        }
}