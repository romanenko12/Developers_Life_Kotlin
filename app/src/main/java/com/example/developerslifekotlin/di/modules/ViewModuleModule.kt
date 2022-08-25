package com.example.developerslifekotlin.di.modules

import androidx.lifecycle.ViewModel
import com.example.developerslifekotlin.di.utils.ViewModelKey
import com.example.developerslifekotlin.gifview.GifViewViewModel
import com.example.developerslifekotlin.gifview.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
interface ViewModuleModule {

    @Binds
    @IntoMap
    @ViewModelKey(GifViewViewModel::class)
    fun bindGifViewViewModel(impl: GifViewViewModel): ViewModel

    companion object {

        @Provides
        fun provideViewModelFactory(
            viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelFactory {
            return ViewModelFactory(viewModels)
        }
    }
}
