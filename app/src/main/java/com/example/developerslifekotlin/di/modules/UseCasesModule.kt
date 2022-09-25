package com.example.developerslifekotlin.di.modules

import com.example.developerslifekotlin.domain.usecases.ClearDatabaseUseCase
import com.example.developerslifekotlin.domain.usecases.ClearDatabaseUseCaseImpl
import com.example.developerslifekotlin.domain.usecases.GetGifUseCase
import com.example.developerslifekotlin.domain.usecases.GetGifUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {

    @Binds
    fun bindGetGifUseCaseImpl(usecase: GetGifUseCaseImpl): GetGifUseCase

    @Binds
    fun bindClearDatabaseUseCaseImpl(usecase: ClearDatabaseUseCaseImpl): ClearDatabaseUseCase
}
