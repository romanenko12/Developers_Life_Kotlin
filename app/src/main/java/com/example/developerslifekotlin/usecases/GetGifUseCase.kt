package com.example.developerslifekotlin.usecases

import com.example.developerslifekotlin.data.database.DatabaseGif
import com.example.developerslifekotlin.data.network.DevelopersLifeApiFilter
import com.example.developerslifekotlin.data.repository.GifRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface GetGifUseCase {
    operator fun invoke(
        category: DevelopersLifeApiFilter,
        number: Int
    ): Single<DatabaseGif>
}

class GetGifUseCaseImpl @Inject constructor(
    private val repository: GifRepository
) : GetGifUseCase {

    private val maxPage = 2000

    override fun invoke(
        category: DevelopersLifeApiFilter,
        number: Int
    ): Single<DatabaseGif> {
        val size = repository.getCount()
            .subscribeOn(Schedulers.io())
            .blockingGet()
        return if (number <= size as Int) {
            repository.getGifFromDatabase(number)
        } else {
            repository.downloadGifFromServer(category, randomNumber())
        }
    }

    private fun randomNumber() = (0..maxPage).random()
}
