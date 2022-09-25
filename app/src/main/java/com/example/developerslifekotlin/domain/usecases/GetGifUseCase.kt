package com.example.developerslifekotlin.domain.usecases

import com.example.developerslifekotlin.domain.entity.DomainGif
import com.example.developerslifekotlin.domain.repository.GifRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface GetGifUseCase {
    operator fun invoke(
        category: String,
        number: Int
    ): Single<DomainGif>
}

class GetGifUseCaseImpl @Inject constructor(
    private val repository: GifRepository
) : GetGifUseCase {

    private val maxPage = 2000

    override fun invoke(
        category: String,
        number: Int
    ): Single<DomainGif> {
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
