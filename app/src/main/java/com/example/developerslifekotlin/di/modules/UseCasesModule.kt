package com.example.developerslifekotlin.di.modules

import com.example.developerslifekotlin.usecases.ClearDatabaseUseCase
import com.example.developerslifekotlin.usecases.ClearDatabaseUseCaseImpl
import com.example.developerslifekotlin.usecases.GetGifUseCase
import com.example.developerslifekotlin.usecases.GetGifUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {

    @Binds
    fun bindGetGifUseCaseImpl(usecase: GetGifUseCaseImpl): GetGifUseCase

    @Binds
    fun bindClearDatabaseUseCaseImpl(usecase: ClearDatabaseUseCaseImpl): ClearDatabaseUseCase
}
