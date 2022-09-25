package com.example.developerslifekotlin.data.repository

import com.example.developerslifekotlin.data.database.GifsDatabaseDao
import com.example.developerslifekotlin.data.network.DevelopersLifeApiService
import com.example.developerslifekotlin.data.mappers.asDatabaseModel
import com.example.developerslifekotlin.data.mappers.asDomainModel
import com.example.developerslifekotlin.domain.entity.DomainGif
import com.example.developerslifekotlin.domain.repository.GifRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class GifRepositoryImpl(
    private val database: GifsDatabaseDao,
    private val server: DevelopersLifeApiService
) : GifRepository {

    override fun downloadGifFromServer(category: String, number: Int): Single<DomainGif> {
        return server.getProperties(category, number)
            .subscribeOn(Schedulers.io())
            .map {
                it.asDomainModel()
            }
            .doOnSuccess {
                database.insert(it.asDatabaseModel())
                    .subscribeOn(Schedulers.io())
                    .subscribeBy()
            }
    }

    override fun getGifFromDatabase(id: Int): Single<DomainGif> {
        return database.get(id).map { it.asDomainModel() }
    }

    override fun getCount(): Maybe<Int> {
        return database.size()
    }

    override fun clearDatabase(): Completable {
        return Completable.fromCallable {
            database.clear()
            database.resetTable()
        }
    }
}
