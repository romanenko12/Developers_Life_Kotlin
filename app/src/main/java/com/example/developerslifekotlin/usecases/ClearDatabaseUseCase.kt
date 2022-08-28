package com.example.developerslifekotlin.usecases

import com.example.developerslifekotlin.data.repository.GifRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

interface ClearDatabaseUseCase {
    operator fun invoke(): Completable
}

class ClearDatabaseUseCaseImpl @Inject constructor(
    private val repository: GifRepository
): ClearDatabaseUseCase {

    override fun invoke(): Completable {
        return repository.clearDatabase()
    }
}
