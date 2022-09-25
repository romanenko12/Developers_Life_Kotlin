package com.example.developerslifekotlin.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

enum class DevelopersLifeApiFilter(val value: String) {
    SHOW_LATEST("latest"),
    SHOW_TOP("top"),
    SHOW_HOT("hot")
}

private const val BASE_URL = "https://developerslife.ru"

const val PAGE_SIZE = 5

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface DevelopersLifeApiService {
    @GET("/{category}/{number}?json=true&pageSize=$PAGE_SIZE")
    fun getProperties(
        @Path("category") category: String,
        @Path("number") number: Int): Single<GifsProperty>
}

object DevelopersLifeApi {
    val retrofitService : DevelopersLifeApiService by lazy { retrofit.create(
        DevelopersLifeApiService::class.java) }
}
