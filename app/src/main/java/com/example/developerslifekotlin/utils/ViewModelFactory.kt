package com.example.developerslifekotlin.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

class ViewModelFactory(
    private val viewModel: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModel[modelClass] ?:
            throw IllegalArgumentException("Unknown ViewModel class")
        return viewModelProvider.get() as T
    }
}
