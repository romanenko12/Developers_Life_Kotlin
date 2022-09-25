package com.example.developerslifekotlin.data.mappers

import com.example.developerslifekotlin.data.network.GifsProperty
import com.example.developerslifekotlin.data.network.PAGE_SIZE
import com.example.developerslifekotlin.domain.entity.DomainGif

fun GifsProperty.asDomainModel(): DomainGif {
    val gif = result[(0 until PAGE_SIZE).random()]
    return DomainGif(
        url = gif.gifURL,
        description = gif.description
    )
}
