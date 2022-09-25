package com.example.developerslifekotlin.di.components
import com.example.developerslifekotlin.di.dependencies.AppDependencies
import com.example.developerslifekotlin.di.modules.AppModule
import com.example.developerslifekotlin.presentation.gifview.GifViewFragment
import com.example.developerslifekotlin.domain.usecases.ClearDatabaseUseCase
import com.example.developerslifekotlin.domain.usecases.GetGifUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class],
    dependencies = [AppDependencies::class]
)
interface AppComponent {

    fun inject(fragment: GifViewFragment)

    @Component.Builder
    interface Builder {

        fun appDependencies(appDependencies: AppDependencies): Builder

        fun build(): AppComponent
    }
}
