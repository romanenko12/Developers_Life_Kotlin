package com.example.developerslifekotlin.di.components
import com.example.developerslifekotlin.di.dependencies.AppDependencies
import com.example.developerslifekotlin.di.modules.AppModule
import com.example.developerslifekotlin.gifview.GifViewFragment
import com.example.developerslifekotlin.usecases.ClearDatabaseUseCase
import com.example.developerslifekotlin.usecases.GetGifUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class],
    dependencies = [AppDependencies::class]
)
interface AppComponent {

    fun inject(fragment: GifViewFragment)

    fun getGifUseCase(): GetGifUseCase
    fun clearDatabaseUseCase(): ClearDatabaseUseCase

    @Component.Builder
    interface Builder {

        fun appDependencies(appDependencies: AppDependencies): Builder

        fun build(): AppComponent
    }
}
