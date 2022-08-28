package com.example.developerslifekotlin.data.repository

import com.example.developerslifekotlin.data.database.DatabaseGif
import com.example.developerslifekotlin.data.database.GifsDatabaseDao
import com.example.developerslifekotlin.data.network.DevelopersLifeApiFilter
import com.example.developerslifekotlin.data.network.DevelopersLifeApiService
import com.example.developerslifekotlin.mappers.asDatabaseModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class GifRepository(
    private val database: GifsDatabaseDao,
    private val server: DevelopersLifeApiService
) {

    fun downloadGifFromServer(category: DevelopersLifeApiFilter, number: Int): Single<DatabaseGif> {
        return server.getProperties(category.value, number)
            .subscribeOn(Schedulers.io())
            .map {
                it.asDatabaseModel()
            }
            .doOnSuccess {
                database.insert(it)
                    .subscribeOn(Schedulers.io())
                    .subscribeBy()
            }
    }

    fun getGifFromDatabase(id: Int): Single<DatabaseGif> {
        return database.get(id)
    }

    fun getCount(): Maybe<Int> {
        return database.size()
    }

    fun clearDatabase(): Completable {
        return Completable.fromCallable {
            database.clear()
            database.resetTable()
        }
    }
}
