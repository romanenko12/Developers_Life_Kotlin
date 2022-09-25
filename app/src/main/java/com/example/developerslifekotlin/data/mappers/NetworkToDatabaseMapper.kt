package com.example.developerslifekotlin.data.mappers

import com.example.developerslifekotlin.data.database.DatabaseGif
import com.example.developerslifekotlin.data.network.GifsProperty
import com.example.developerslifekotlin.data.network.PAGE_SIZE

fun GifsProperty.asDatabaseModel(): DatabaseGif {
    val gif = result[(0 until PAGE_SIZE).random()]
    return  DatabaseGif(
        url = gif.gifURL,
        description = gif.description)
}
