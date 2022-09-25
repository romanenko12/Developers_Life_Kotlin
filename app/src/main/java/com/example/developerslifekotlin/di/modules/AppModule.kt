package com.example.developerslifekotlin.di.modules

import android.content.Context
import com.example.developerslifekotlin.data.database.DatabaseInit
import com.example.developerslifekotlin.data.database.GifsDatabaseDao
import com.example.developerslifekotlin.data.network.DevelopersLifeApi
import com.example.developerslifekotlin.data.network.DevelopersLifeApiService
import com.example.developerslifekotlin.data.repository.GifRepositoryImpl
import com.example.developerslifekotlin.domain.repository.GifRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module(
    includes = [
        ViewModuleModule::class,
        UseCasesModule::class
    ]
)
class AppModule {

    @Provides
    @Reusable
    fun provideRepository(
        localDataSource: GifsDatabaseDao,
        remoteDataSource: DevelopersLifeApiService
    ): GifRepository {
        return GifRepositoryImpl(
            localDataSource,
            remoteDataSource
        )
    }

    @Provides
    @Reusable
    fun provideRemoteDataSource(context: Context): GifsDatabaseDao {
        return DatabaseInit.getDatabase(context).gifsDatabaseDao
    }

    @Provides
    @Reusable
    fun provideLocalDataSource(): DevelopersLifeApiService {
        return DevelopersLifeApi.retrofitService
    }
}
