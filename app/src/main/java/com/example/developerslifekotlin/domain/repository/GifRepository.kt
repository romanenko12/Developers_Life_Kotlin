package com.example.developerslifekotlin.domain.repository

import com.example.developerslifekotlin.domain.entity.DomainGif
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface GifRepository {

    fun downloadGifFromServer(category: String, number: Int): Single<DomainGif>

    fun getGifFromDatabase(id: Int): Single<DomainGif>

    fun getCount(): Maybe<Int>

    fun clearDatabase(): Completable
}
