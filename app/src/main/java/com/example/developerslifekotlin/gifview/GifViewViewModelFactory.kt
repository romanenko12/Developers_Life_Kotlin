package com.example.developerslifekotlin.gifview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.developerslifekotlin.repository.GifRepository

class GifViewViewModelFactory(
    private val repository: GifRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GifViewViewModel::class.java)) {
            return GifViewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}